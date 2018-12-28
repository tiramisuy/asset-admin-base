package com.jubao.common.support.shiro.session;

import com.jubao.common.support.redis.RedisCacheManager;
import com.jubao.common.utils.SpringContextUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.DependsOn;

import java.io.Serializable;

/**
 * 基于redis的sessionDAO
 * 
 */
@DependsOn("springContextUtils")
public class UserSessionDAO extends EnterpriseCacheSessionDAO {

    private static final Logger LOG = LoggerFactory.getLogger(UserSessionDAO.class);

    private static final String SHIRO_SESSION_ID = "dp:session:id:";

    private RedisCacheManager redisCacheManager;

    public UserSessionDAO() {
        redisCacheManager = (RedisCacheManager) SpringContextUtils.getBean("redisCacheManager");
    }

    /**
     * 更新session的最后一次访问时间
     * @param session
     */
    @Override
    protected void doUpdate(Session session) {
        try {
            // 如果会话过期/停止 没必要再更新了
            if(session instanceof ValidatingSession && !((ValidatingSession)session).isValid()) {
                return;
            }
        } catch (Exception e) {
            LOG.warn("验证session失败", e.getMessage());
        }

        try {
            if (session instanceof UserSession) {
                // 如果没有主要字段(除lastAccessTime以外其他字段)发生改变
                UserSession userSession = (UserSession) session;
                if (!userSession.isChanged()) {
                    return;
                }
                String sessionKey = generateSessionKey(session);
                setSession(sessionKey, session);
                LOG.debug("doUpdate >>>>> sessionId：{}", session.getId());
            }
        } catch (Exception e) {
            LOG.warn("更新session失败", e.getMessage());
        }

    }

    /**
     * 删除session
     * @param session
     */
    @Override
    protected void doDelete(Session session) {
        try {
            super.doDelete(session);
            String sessionKey = generateSessionKey(session);
            redisCacheManager.del(sessionKey);
            LOG.debug("doDelete >>>>> sessionId：{}", session.getId());
        } catch (Exception e) {
            LOG.warn("删除session失败", e.getMessage());
        }
    }

    /**
     * 创建session，保存到redis
     * @param session
     * @return
     */
    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = super.doCreate(session);
        assignSessionId(session, sessionId);
        try {
            String sessionKey = generateSessionKey(sessionId);
            setSession(sessionKey, session);
            LOG.debug("doCreate >>>>> sessionId：{}", sessionId);
        } catch (Exception e) {
            LOG.warn("创建session失败", e.getMessage());
        }
        return sessionId;
    }

    /**
     * 如果Session中没有登陆信息就调用doReadSession方法从Redis中重读
     * @param sessionId
     * @return
     */
    @Override
    public Session readSession(Serializable sessionId) {
        Session session = getCachedSession(sessionId);
        if (session == null
                || session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY) == null) {
            session = this.doReadSession(sessionId);
            if (session == null) {
                throw new UnknownSessionException("There is no session with id: {" + sessionId + "}");
            } else {
                cache(session, session.getId());
            }
        }
        return session;
    }

    /**
     * 获取session
     * @param sessionId
     * @return
     */
    @Override
    protected Session doReadSession(Serializable sessionId) {
        Session session = null;
        try {
            session = getSession(generateSessionKey(sessionId));
            LOG.debug("doReadSession >>>>> sessionId：{}", session.getId());
        } catch (Exception e) {
            LOG.warn("读取session失败", e.getMessage());
        }
        return session;
    }

    private Session getSession(String key) {
        String session = String.valueOf(redisCacheManager.get(key));
        if (StringUtils.isNotEmpty(session)) {
            return SerializableUtils.deserialize(session);
        }
        return null;
    }

    private void setSession(String key, Session session) {
        redisCacheManager.set(key, SerializableUtils.serialize(session), session.getTimeout() / 1000);
    }

    private String generateSessionKey(Session session) {
        return SHIRO_SESSION_ID + session.getId();
    }

    private String generateSessionKey(Serializable sessionId) {
        return SHIRO_SESSION_ID + sessionId;
    }

}

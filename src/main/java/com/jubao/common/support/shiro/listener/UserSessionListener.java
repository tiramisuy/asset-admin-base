package com.jubao.common.support.shiro.listener;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * session监听器
 * 
 */
public class UserSessionListener implements SessionListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserSessionListener.class);

    @Override
    public void onStart(Session session) {
        LOGGER.debug("会话创建：{}", session.getId());
    }

    @Override
    public void onStop(Session session) {
        LOGGER.debug("会话停止：{}", session.getId());
    }

    @Override
    public void onExpiration(Session session) {
        LOGGER.debug("会话过期：{}", session.getId());
    }
}

package com.jubao.common.support.shiro.session;

import com.jubao.common.utils.WebUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.SessionFactory;
import org.apache.shiro.web.session.mgt.DefaultWebSessionContext;

import javax.servlet.http.HttpServletRequest;

/**
 * session工厂
 * 
 */
public class UserSessionFactory implements SessionFactory{

    @Override
    public Session createSession(SessionContext initData) {
        UserSession session = new UserSession();
        HttpServletRequest request = (HttpServletRequest)initData.get(DefaultWebSessionContext.class.getName() + ".SERVLET_REQUEST");
        session.setHost(getIpAddress(request));
        return session;
    }

    public static String getIpAddress(HttpServletRequest request) {
        return WebUtils.getIpAddr(request);
    }

}

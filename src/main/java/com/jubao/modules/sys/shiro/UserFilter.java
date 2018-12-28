package com.jubao.modules.sys.shiro;

import com.jubao.common.entity.R;
import com.jubao.common.utils.JSONUtils;
import com.jubao.common.utils.WebUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 处理ajax请求session过期
 * 
 */
public class UserFilter extends AccessControlFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        if (isLoginRequest(request, response)) {
            return true;
        } else {
            Subject subject = getSubject(request, response);
            return subject.getPrincipal() != null;
        }
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        HttpServletResponse httpServletResponse = (HttpServletResponse)response;

        /**
         * 如果是ajax请求则不进行跳转
         */
        if (WebUtils.isAjax(httpServletRequest)) {
            httpServletResponse.setHeader("sessionstatus", "timeout");

            R timeout = R.error(401, "登录超时，请重新登录");
            WebUtils.write(httpServletResponse, JSONUtils.beanToJson(timeout));
            return false;
        } else {

            /**
             * 第一次点击页面
             */
            String referer = httpServletRequest.getHeader("Referer");
            if (referer == null) {
                saveRequestAndRedirectToLogin(request, response);
                return false;
            } else {

                /**
                 * 从别的页面跳转过来的
                 */
                if (SecurityUtils.getSubject().getSession().getAttribute("sessionFlag") == null) {
                    httpServletRequest.setAttribute("errorMsg", "登录超时，请重新登录");
                    httpServletRequest.getRequestDispatcher("/login").forward(request, response);
                    return false;
                } else {
                    saveRequestAndRedirectToLogin(request, response);
                    return false;
                }
            }
        }
    }
}

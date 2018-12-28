package com.jubao.modules.sys.shiro;

import com.jubao.common.entity.R;
import com.jubao.common.utils.JSONUtils;
import com.jubao.common.utils.WebUtils;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户权限过滤器
 * 
 */
public class UserPermFilter extends PermissionsAuthorizationFilter {

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        if (WebUtils.isAjax(httpServletRequest)) {
            // ajax请求返回json
            R error = R.error("没有权限，请联系管理员授权");
            WebUtils.write(httpServletResponse, JSONUtils.beanToJson(error));
        } else {
            // 非异步请求直接跳转权限错误页面
            httpServletResponse.sendRedirect("/error/403");
        }
        return false;
    }
}

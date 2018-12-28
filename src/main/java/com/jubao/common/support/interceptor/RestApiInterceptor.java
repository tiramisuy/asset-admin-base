package com.jubao.common.support.interceptor;

import io.jsonwebtoken.JwtException;
import com.jubao.common.annotation.RestAnon;
import com.jubao.common.constant.RestApiConstant;
import com.jubao.common.utils.*;
import com.jubao.modules.sys.entity.SysUserEntity;
import com.jubao.modules.sys.entity.SysUserTokenEntity;
import com.jubao.modules.sys.service.SysUserService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * rest api拦截器
 * 
 */
@DependsOn("springContextUtils")
public class RestApiInterceptor extends HandlerInterceptorAdapter {

    private static final Logger log = LoggerFactory.getLogger(RestApiInterceptor.class);

    private SysUserService userService = (SysUserService) SpringContextUtils.getBean("sysUserService");

    private JwtUtils jwtUtils = SpringContextUtils.getBean("jwtUtils", JwtUtils.class);

    /**
     * 拦截
     * @param request
     * @param response
     * @param handler
     * @return
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 静态资源请求拦截
        if (handler instanceof ResourceHttpRequestHandler) {
            return true;
        }
        // 有RestAnon注解的方法不拦截
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            if (handlerMethod.hasMethodAnnotation(RestAnon.class)) {
                return true;
            }
        }
        return checkToken(request, response);
    }

    /**
     * token校验
     * @param request
     * @param response
     * @return
     */
    private boolean checkToken(HttpServletRequest request, HttpServletResponse response) {
        // 登录 或 有效状态校验 请求直接通过
        String requestPath = request.getServletPath();
        if (RestApiConstant.AUTH_REQUEST.equals(requestPath) || RestApiConstant.AUTH_CHECK.equals(requestPath)) {
            return true;
        }
        // 校验请求是否包含验证信息
        String token = getToken(request);
        if (StringUtils.isBlank(token)) {
            WebUtils.write(response, JSONUtils.beanToJson(RestApiConstant.TokenErrorEnum.TOKEN_NOT_FOUND.getResp()));
            return false;
        }
        try {
            boolean flag = jwtUtils.isExpred(token);
            if (flag) {
                WebUtils.write(response, JSONUtils.beanToJson(RestApiConstant.TokenErrorEnum.TOKEN_EXPIRED.getResp()));
                return false;
            }
        } catch (JwtException e) {
            log.info("token解析异常：{}", token);
            return false;
        }

        // token校验
        SysUserTokenEntity sysUserTokenEntity = userService.getUserTokenByToken(jwtUtils.getMd5Key(token));
        if (sysUserTokenEntity == null) {
            WebUtils.write(response, JSONUtils.beanToJson(RestApiConstant.TokenErrorEnum.TOKEN_INVALID.getResp()));
            return false;
        }

        // token中的userId和数据库中userId是否一致
        if (sysUserTokenEntity.getUserId() != Long.parseLong(jwtUtils.getUserId(token))) {
            WebUtils.write(response, JSONUtils.beanToJson(RestApiConstant.TokenErrorEnum.TOKEN_INVALID.getResp()));
            return false;
        }

        // token过期
        if (TokenUtils.isExpired(sysUserTokenEntity.getGmtExpire())) {
            WebUtils.write(response, JSONUtils.beanToJson(RestApiConstant.TokenErrorEnum.TOKEN_EXPIRED.getResp()));
            return false;
        }

        // 用户校验
        SysUserEntity sysUserEntity = userService.getUserByIdForToken(sysUserTokenEntity.getUserId());
        if (sysUserEntity.getStatus() == 0) {
            WebUtils.write(response, JSONUtils.beanToJson(RestApiConstant.TokenErrorEnum.USER_DISABLE.getResp()));
            return false;
        }

        return true;
    }

    /**
     * 获取token
     * @param request
     * @return
     */
    private String getToken(HttpServletRequest request) {
        // 请求头token
        String token = request.getHeader(RestApiConstant.AUTH_TOKEN);
        if (StringUtils.isBlank(token)) {
            // 请求参数token
            return request.getParameter(RestApiConstant.AUTH_TOKEN);
        }
        return token;
    }

}

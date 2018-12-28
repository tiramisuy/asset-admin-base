package com.jubao.common.support.config;

import com.jubao.common.support.properties.GlobalProperties;
import com.jubao.common.support.shiro.listener.UserSessionListener;
import com.jubao.common.support.shiro.session.UserSessionDAO;
import com.jubao.common.support.shiro.session.UserSessionFactory;
import com.jubao.modules.sys.shiro.ShiroPermsFilterFactoryBean;
import com.jubao.modules.sys.shiro.UserFilter;
import com.jubao.modules.sys.shiro.UserPermFilter;
import com.jubao.modules.sys.shiro.UserRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.*;

import javax.servlet.Filter;

/**
 * shiro配置
 * 
 */
@DependsOn("springContextUtils")
@Configuration
public class ShiroConfig {

    /**
     * 安全管理器
     * @param sessionManager
     * @return
     */
    @Bean
    public SecurityManager securityManager(SessionManager sessionManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setSessionManager(sessionManager);
        securityManager.setRealm(this.userRealm());
        return securityManager;
    }

    /**
     * session管理器
     * @return
     */
    @Bean
    public SessionManager sessionManager(GlobalProperties globalProperties){
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionValidationSchedulerEnabled(true);
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        sessionManager.setDeleteInvalidSessions(true);
        if (globalProperties.isRedisSessionDao()) {
            // 开启redis会话管理器
            sessionManager.setSessionFactory(new UserSessionFactory());
            sessionManager.setSessionDAO(new UserSessionDAO());
            List<SessionListener> sessionListeners = new ArrayList<>();
            sessionListeners.add(new UserSessionListener());
            sessionManager.setSessionListeners(sessionListeners);
        }
        return sessionManager;
    }

    /**
     * 用户realm
     * @return
     */
    @Bean
    public UserRealm userRealm(){
        return new UserRealm();
    }

    /**
     * shiro过滤器
     * /rest/**，请求采用token验证（com.jubao.common.support.interceptor.RestApiInterceptor）
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroPermsFilterFactoryBean shiroFilter = new ShiroPermsFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);

        shiroFilter.setLoginUrl("/login");

        shiroFilter.setSuccessUrl("/");

        shiroFilter.setUnauthorizedUrl("/error/403");

        //user过滤器，处理ajax请求超时不跳转情况
        Map<String, Filter> filters = new HashMap<>(2);
        filters.put("user", new UserFilter());
        filters.put("perms", new UserPermFilter());
        shiroFilter.setFilters(filters);

        Map<String, String> filterMap = new LinkedHashMap<>(5);
        filterMap.put("/static/**", "anon");
        filterMap.put("/error/**", "anon");
        filterMap.put("/login", "anon");
        filterMap.put("/captcha.jpg", "anon");
        filterMap.put("/rest/**", "anon");
        shiroFilter.setFilterChainDefinitionMap(filterMap);

        return shiroFilter;
    }

    /**
     * shiro生命周期处理器
     * @return
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 使用cglib方式创建代理对象
     * @return
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
        proxyCreator.setProxyTargetClass(true);
        return proxyCreator;
    }

    /**
     * 启用注解
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

}

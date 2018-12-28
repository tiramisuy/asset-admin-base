package com.jubao.modules.sys.controller;

import com.jubao.common.utils.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jubao.common.utils.ShiroUtils;
import com.jubao.modules.sys.entity.SysUserEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Controller公共组件
 * 
 */
public abstract class AbstractController {

	/**
	 * 日志
	 */
	protected Logger LOGGER = LoggerFactory.getLogger(getClass());

	/**
	 * 获取参数
	 * @param name 请求参数名称
	 * @return 请求参数值
	 */
	protected String getParam(String name) {
		return WebUtils.getRequest().getParameter(name);
	}

	/**
	 * 设置属性
	 * @param key 属性名
	 * @param value 属性值
	 */
	protected void setAttr(String key, Object value) {
		WebUtils.getRequest().setAttribute(key, value);
	}

	/**
	 * 获取httpServletRequest
	 * @return HttpServletRequest
	 */
	protected HttpServletRequest getHttpServletRequest() {
		return WebUtils.getRequest();
	}

	/**
	 * 获取httpServletResponse
	 * @return HttpServletResponse
	 */
	protected HttpServletResponse getHttpServletResponse() {
		return WebUtils.getResponse();
	}

	/**
	 * 获取session：如果当前请求没有session，则创建一个
	 * @return HttpSession
	 */
	protected HttpSession getSession() {
		return WebUtils.getRequest().getSession();
	}

	/**
	 * 获取session：如果当前请求没有session，true则创建一个，false则返回null
	 * @param create 是否创建，true：创建，false：不创建，返回null
	 * @return HttpSession
	 */
	protected HttpSession getSession(boolean create) {
		return WebUtils.getRequest().getSession(create);
	}

	/**
	 * 获取当前用户entity
	 * @return SysUserEntity
	 */
	protected SysUserEntity getUser() {
		return ShiroUtils.getUserEntity();
	}

	/**
	 * 获取当前用户id
	 * @return 用户id
	 */
	protected Long getUserId() {
		return getUser().getUserId();
	}

	/**
	 * 重定向
	 * @param page
	 * @return 重定向全路径
	 */
	protected String redirect(String page) {
		return "redirect:".concat(page);
	}

	/**
	 * beetl视图
	 * @param page
	 * @return html全路径
	 */
	protected String html(String page) {
		return page.concat(".html");
	}
	
}

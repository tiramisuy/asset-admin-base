package com.jubao.modules.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * 系统页面视图
 * 
 */
@Controller
public class SysPageController {

	/**
	 * 页面跳转
	 * @param module
	 * @param function
	 * @param url
	 * @return
	 */
	@RequestMapping("/{module}/{function}/{url}.html")
	public String page(@PathVariable("module") String module, @PathVariable("function") String function,
			@PathVariable("url") String url) {
		return "/" + module + "/" + function + "/" + url + ".html";
	}

	/**
	 * 404页面
	 * @return
	 */
	@RequestMapping("/error/404")
	public String notFoundPage() {
		return "/error/404.html";
	}

	/**
	 * 403页面
	 * @return
	 */
	@RequestMapping("/error/403")
	public String noAuthPage() {
		return "/error/403.html";
	}

	/**
	 * 500页面
	 * @return
	 */
	@RequestMapping("/error/500")
	public String sysError() {
		return "/error/500.html";
	}

	/**
	 * 系统首页
	 * @return
	 */
	@RequestMapping("/dashboard")
	public String main() {
		return "/system/dashboard.html";
	}

}

package com.jubao.modules.sys.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统用户Token
 *
 */
public class SysUserTokenEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 用户id
	 */
	private Long userId;
	
	/**
	 * token
	 */
	private String token;
	
	/**
	 * 过期时间
	 */
	private Date gmtExpire;
	
	/**
	 * 更新时间
	 */
	private Date gmtModified;

	public SysUserTokenEntity() {
		super();
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getGmtExpire() {
		return gmtExpire;
	}

	public void setGmtExpire(Date gmtExpire) {
		this.gmtExpire = gmtExpire;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

}

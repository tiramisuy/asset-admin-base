package com.jubao.modules.sys.entity;


import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * 角色
 *
 */
public class SysRoleEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 角色id
	 */
	private Long roleId;
	
	/**
	 * 机构id
	 */
	private Long orgId;
	
	/**
	 * 机构名称
	 */
	private String orgName;

	/**
	 * 角色名称
	 */
	private String roleName;
	
	/**
	 * 角色标识
	 */
	private String roleSign;

	/**
	 * 备注
	 */
	private String remark;
	
	/**
	 * 创建者id
	 */
	private Long userIdCreate;
	
	private List<Long> menuIdList;
	
	private List<Long> orgIdList;
	
	/**
	 * 创建时间
	 */
	private Timestamp gmtCreate;
	
	/**
	 * 修改时间
	 */
	private Timestamp gmtModified;

	public SysRoleEntity() {
		super();
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleSign() {
		return roleSign;
	}

	public void setRoleSign(String roleSign) {
		this.roleSign = roleSign;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getUserIdCreate() {
		return userIdCreate;
	}

	public void setUserIdCreate(Long userIdCreate) {
		this.userIdCreate = userIdCreate;
	}

	public List<Long> getMenuIdList() {
		return menuIdList;
	}

	public void setMenuIdList(List<Long> menuIdList) {
		this.menuIdList = menuIdList;
	}

	public List<Long> getOrgIdList() {
		return orgIdList;
	}

	public void setOrgIdList(List<Long> orgIdList) {
		this.orgIdList = orgIdList;
	}

	public Timestamp getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Timestamp gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Timestamp getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Timestamp gmtModified) {
		this.gmtModified = gmtModified;
	}
	
}

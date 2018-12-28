package com.jubao.modules.sys.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * 组织架构
 *
 */
public class SysOrgEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 机构id
	 */
	private Long orgId;
	
	/**
	 * 上级机构id，一级部门为0
	 */
	private Long parentId;
	
	/**
	 * 上级机构名称
	 */
	private String parentName;
	
	/**
	 * 机构编码
	 */
	private String code;
	
	/**
	 * 机构名称
	 */
	private String name;
	
	/**
	 * 排序
	 */
	private Integer orderNum;
	
	/**
	 * 可用标识，1：可用，0：不可用
	 */
	private Integer status;
	
	/**
	 * 创建时间
	 */
	private Timestamp gmtCreate;
	
	/**
	 * 修改时间
	 */
	private Timestamp gmtModified;
	
	/**
	 * ztree属性
	 */
	private Boolean open;
	
	private List<?> list;

	public SysOrgEntity() {
		super();
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public Boolean getOpen() {
		return open;
	}

	public void setOpen(Boolean open) {
		this.open = open;
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}
	
}

package com.jubao.modules.sys.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import com.jubao.common.utils.CommonUtils;

/**
 * 行政区域
 */
public class SysAreaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 区域id
	 */
	private Long areaId;
	
	/**
	 * 区域代码
	 */
	private String areaCode;
	
	/**
	 * 父级代码，省级为0
	 */
	private String parentCode;
	
	/**
	 * 父级名称
	 */
	private String parentName;
	
	/**
	 * 区域名称
	 */
	private String name;
	
	/**
	 * 层级，1：省级，2：地市，3：区县
	 */
	private Integer layer;
	
	/**
	 * 排序
	 */
	private Integer orderNum;
	
	/**
	 * 状态，1：显示，0：隐藏
	 */
	private Integer status;
	
	/**
	 * 备注
	 */
	private String remark;
	
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
	
	private Boolean isParent;
	
	private Integer size;
	
	private List<?> list;

	public SysAreaEntity() {
		super();
	}

	public Long getAreaId() {
		return areaId;
	}

	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getLayer() {
		return layer;
	}

	public void setLayer(Integer layer) {
		this.layer = layer;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public Boolean getIsParent() {
		return isParent;
	}

	public void setIsParent(Boolean isParent) {
		this.isParent = isParent;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}
	
	public void checkParent() {
		if(CommonUtils.isIntThanZero(this.size)) {
			this.isParent = true;
		} else {
			this.isParent = false;
		}
	}
	
	public void checkParentName() {
		if(this.parentCode.equals("0")) {
			this.parentName = "省级区域";
		}
	}
	
}

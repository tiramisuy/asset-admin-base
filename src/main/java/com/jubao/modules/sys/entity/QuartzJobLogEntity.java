package com.jubao.modules.sys.entity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 定时任务日志
 *
 */
public class QuartzJobLogEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 记录id
	 */
	private Long logId;
	
	/**
	 * 任务id
	 */
	private Long jobId;
	
	/**
	 * spring bean 名称
	 */
	private String beanName;
	
	/**
	 * 方法名
	 */
	private String methodName;
	
	/**
	 * 参数
	 */
	private String params;
	
	/**
	 * 状态，0：失败，1：成功
	 */
	private Integer status;
	
	/**
	 * 错误信息
	 */
	private String error;
	
	/**
	 * 耗时（ms）
	 */
	private Integer times;
	
	/**
	 * 创建时间
	 */
	private Timestamp gmtCreate;

	public QuartzJobLogEntity() {
		super();
	}

	public Long getLogId() {
		return logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
	}

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public Integer getTimes() {
		return times;
	}

	public void setTimes(Integer times) {
		this.times = times;
	}

	public Timestamp getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Timestamp gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

}

package com.jubao.modules.sys.dao;

import org.apache.ibatis.annotations.Mapper;

import com.jubao.modules.sys.entity.SysLogEntity;

/**
 * 系统日志
 * 
 */
@Mapper
public interface SysLogMapper extends BaseMapper<SysLogEntity> {

	/**
	 * 批量删除
	 * @return
	 */
	int batchRemoveAll();
	
}

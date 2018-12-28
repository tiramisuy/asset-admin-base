package com.jubao.modules.sys.dao;

import org.apache.ibatis.annotations.Mapper;

import com.jubao.modules.sys.entity.SysUserTokenEntity;

/**
 * 用户token
 * 
 */
@Mapper
public interface SysUserTokenMapper extends BaseMapper<SysUserTokenEntity> {

	/**
	 * 根据token查询
	 * @param token
	 * @return
	 */
	SysUserTokenEntity getByToken(String token);

	/**
	 * 根据用户id查询
	 * @param userId
	 * @return
	 */
	SysUserTokenEntity getByUserId(Long userId);
	
}

package com.jubao.modules.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import com.jubao.modules.sys.entity.SysRoleOrgEntity;

/**
 * 角色与机构的关系
 * 
 */
@Mapper
public interface SysRoleOrgMapper extends BaseMapper<SysRoleOrgEntity> {

	/**
	 * 查询角色所有机构id集合
	 * @param roleId
	 * @return
	 */
	List<Long> listOrgId(Long roleId);

	/**
	 * 根据机构id删除
	 * @param id
	 * @return
	 */
	int batchRemoveByOrgId(Long[] id);

	/**
	 * 根据角色id删除
	 * @param id
	 * @return
	 */
	int batchRemoveByRoleId(Long[] id);
	
}

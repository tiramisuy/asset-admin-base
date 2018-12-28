package com.jubao.modules.sys.dao;

import org.apache.ibatis.annotations.Mapper;
import com.jubao.modules.sys.entity.SysOrgEntity;

import java.util.List;

/**
 * 组织架构
 *
 */
@Mapper
public interface SysOrgMapper extends BaseMapper<SysOrgEntity> {

	/**
	 * 统计子机构数量
	 * @param parentId
	 * @return
	 */
	int countOrgChildren(Long parentId);

	/**
	 * 查询子机构集合
	 * @param parentId
	 * @return
	 */
	List<Long> listOrgChildren(Long parentId);
	
}

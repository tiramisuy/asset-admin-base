package com.jubao.modules.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.jubao.modules.sys.entity.SysMenuEntity;

/**
 * 系统菜单dao
 * 
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenuEntity> {

	/**
	 * 根据父级id查询菜单
	 * @param parentId
	 * @return
	 */
	List<SysMenuEntity> listParentId(Long parentId);

	/**
	 * 查询菜单目录和菜单集合
	 * @return
	 */
	List<SysMenuEntity> listNotButton();

	/**
	 * 用户权限菜单
	 * @param userId
	 * @return
	 */
	List<String> listUserPerms(Long userId);

	/**
	 * 菜单子节点个数
	 * @param parentId
	 * @return
	 */
	int countMenuChildren(Long parentId);

}

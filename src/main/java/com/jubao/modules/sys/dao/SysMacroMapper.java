package com.jubao.modules.sys.dao;

import com.jubao.modules.sys.entity.SysMacroEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * 通用字典
 * 
 */
@Mapper
public interface SysMacroMapper extends BaseMapper<SysMacroEntity> {

	/**
	 * 查询目录集合
	 * @return
	 */
	List<SysMacroEntity> listNotMacro();

	/**
	 * 查询子节点个数
	 * @param typeId
	 * @return
	 */
	int countMacroChildren(Long typeId);

	/**
	 * 根据类型查询所有参数
	 * @param type
	 * @return
	 */
	List<SysMacroEntity> listMacroValue(String type);
	
}

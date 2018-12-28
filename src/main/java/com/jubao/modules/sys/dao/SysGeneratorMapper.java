package com.jubao.modules.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import com.jubao.common.entity.Page;
import com.jubao.common.entity.Query;
import com.jubao.modules.sys.entity.ColumnEntity;
import com.jubao.modules.sys.entity.TableEntity;

/**
 * 代码生成器
 * 
 */
@Mapper
public interface SysGeneratorMapper {

	/**
	 * 查询所有表格
	 * @param page
	 * @param query
	 * @return
	 */
	List<TableEntity> listTable(Page<TableEntity> page, Query query);

	/**
	 * 根据名称查询
	 * @param tableName
	 * @return
	 */
	TableEntity getTableByName(String tableName);

	/**
	 * 查询所有列
	 * @param tableName
	 * @return
	 */
	List<ColumnEntity> listColumn(String tableName);
	
}

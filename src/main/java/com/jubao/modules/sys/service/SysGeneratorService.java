package com.jubao.modules.sys.service;

import com.jubao.common.entity.Page;
import com.jubao.modules.sys.entity.GeneratorParamEntity;
import com.jubao.modules.sys.entity.TableEntity;

import java.util.Map;

/**
 * 代码生成器
 * 
 */
public interface SysGeneratorService {

	/**
	 * 分页查询表格
	 * @param params
	 * @return
	 */
	Page<TableEntity> listTable(Map<String, Object> params);

	/**
	 * 生成代码
	 * @param params
	 * @return
	 */
	byte[] generator(GeneratorParamEntity params);
	
}

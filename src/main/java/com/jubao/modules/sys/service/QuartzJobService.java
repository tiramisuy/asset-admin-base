package com.jubao.modules.sys.service;

import com.jubao.common.entity.Page;
import com.jubao.common.entity.R;
import com.jubao.modules.sys.entity.QuartzJobEntity;

import java.util.Map;

/**
 * 定时任务
 * 
 */
public interface QuartzJobService {

	/**
	 * 分页查询任务
	 * @param params
	 * @return
	 */
	Page<QuartzJobEntity> list(Map<String, Object> params);

	/**
	 * 新增任务
	 * @param job
	 * @return
	 */
	R saveQuartzJob(QuartzJobEntity job);

	/**
	 * 根据id查询任务
	 * @param jobId
	 * @return
	 */
	R getQuartzJobById(Long jobId);

	/**
	 * 修改任务
	 * @param job
	 * @return
	 */
	R updateQuartzJob(QuartzJobEntity job);

	/**
	 * 批量删除任务
	 * @param id
	 * @return
	 */
	R batchRemoveQuartzJob(Long[] id);

	/**
	 * 立即运行任务
	 * @param id
	 * @return
	 */
	R run(Long[] id);

	/**
	 * 暂停任务
	 * @param id
	 * @return
	 */
	R pause(Long[] id);

	/**
	 * 恢复任务
	 * @param id
	 * @return
	 */
	R resume(Long[] id);
	
}

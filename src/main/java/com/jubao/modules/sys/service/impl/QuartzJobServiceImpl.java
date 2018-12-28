package com.jubao.modules.sys.service.impl;

import com.jubao.common.constant.SystemConstant;
import com.jubao.common.entity.Page;
import com.jubao.common.entity.Query;
import com.jubao.common.entity.R;
import com.jubao.common.utils.CommonUtils;
import com.jubao.modules.sys.dao.QuartzJobMapper;
import com.jubao.modules.sys.entity.QuartzJobEntity;
import com.jubao.modules.sys.quartz.ScheduleUtils;
import com.jubao.modules.sys.service.QuartzJobService;
import org.quartz.CronTrigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

/**
 * 定时任务
 * 
 */
@DependsOn("springContextUtils")
@Service("quartzJobService")
public class QuartzJobServiceImpl implements QuartzJobService {
	
	@Autowired
	private QuartzJobMapper quartzJobMapper;
	
	/**
	 * 项目启动，初始化任务
	 */
	@PostConstruct
	public void init() {
		List<QuartzJobEntity> jobList = quartzJobMapper.list();
		for(QuartzJobEntity job : jobList) {
			CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(job.getJobId());
            //如果不存在，则创建
            if(cronTrigger == null) {
                ScheduleUtils.createScheduleJob(job);
            }else {
                ScheduleUtils.updateScheduleJob(job);
            }
		}
	}

	/**
	 * 分页查询任务
	 * @param params
	 * @return
	 */
	@Override
	public Page<QuartzJobEntity> list(Map<String, Object> params) {
		Query query = new Query(params);
		Page<QuartzJobEntity> page = new Page<>(query);
		quartzJobMapper.listForPage(page, query);
		return page;
	}

	/**
	 * 新增任务
	 * @param job
	 * @return
	 */
	@Override
	public R saveQuartzJob(QuartzJobEntity job) {
		job.setStatus(SystemConstant.ScheduleStatus.NORMAL.getValue());
		int count = quartzJobMapper.save(job);
		ScheduleUtils.createScheduleJob(job);
		return CommonUtils.msg(count);
	}

	/**
	 * 根据id查询任务
	 * @param jobId
	 * @return
	 */
	@Override
	public R getQuartzJobById(Long jobId) {
		QuartzJobEntity job = quartzJobMapper.getObjectById(jobId);
		return CommonUtils.msg(job);
	}

	/**
	 * 更新任务
	 * @param job
	 * @return
	 */
	@Override
	public R updateQuartzJob(QuartzJobEntity job) {
		int count = quartzJobMapper.update(job);
		ScheduleUtils.updateScheduleJob(job);
		return CommonUtils.msg(count);
	}

	/**
	 * 批量删除任务
	 * @param id
	 * @return
	 */
	@Override
	public R batchRemoveQuartzJob(Long[] id) {
		for(Long jobId : id) {
			ScheduleUtils.deleteScheduleJob(jobId);
		}
		int count = quartzJobMapper.batchRemove(id);
		return CommonUtils.msg(id, count);
	}

	/**
	 * 立即运行任务
	 * @param id
	 * @return
	 */
	@Override
	public R run(Long[] id) {
		for(Long jobId : id) {
			ScheduleUtils.run(quartzJobMapper.getObjectById(jobId));
		}
		return CommonUtils.msg(1);
	}

	/**
	 * 暂停任务
	 * @param id
	 * @return
	 */
	@Override
	public R pause(Long[] id) {
		for(Long jobId : id) {
			ScheduleUtils.pauseJob(jobId);
		}
		Query query = new Query();
		query.put("jobIdList", id);
		query.put("status", SystemConstant.ScheduleStatus.PAUSE.getValue());
		int count = quartzJobMapper.batchUpdate(query);
		return CommonUtils.msg(id, count);
	}

	/**
	 * 恢复任务
	 * @param id
	 * @return
	 */
	@Override
	public R resume(Long[] id) {
		for(Long jobId : id) {
			ScheduleUtils.resumeJob(jobId);
		}
		Query query = new Query();
		query.put("jobIdList", id);
		query.put("status", SystemConstant.ScheduleStatus.NORMAL.getValue());
		int count = quartzJobMapper.batchUpdate(query);
		return CommonUtils.msg(id, count);
	}

}

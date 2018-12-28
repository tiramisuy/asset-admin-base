package com.jubao.modules.sys.service.impl;

import com.jubao.common.entity.Page;
import com.jubao.common.entity.Query;
import com.jubao.common.entity.R;
import com.jubao.common.utils.CommonUtils;
import com.jubao.modules.sys.dao.SysRoleMapper;
import com.jubao.modules.sys.dao.SysRoleMenuMapper;
import com.jubao.modules.sys.dao.SysRoleOrgMapper;
import com.jubao.modules.sys.dao.SysUserRoleMapper;
import com.jubao.modules.sys.entity.SysRoleEntity;
import com.jubao.modules.sys.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 系统角色
 * 
 */
@Service("sysRoleService")
public class SysRoleServiceImpl implements SysRoleService {

	@Autowired
	private SysRoleMapper sysRoleMapper;

	@Autowired
	private SysUserRoleMapper sysUserRoleMapper;

	@Autowired
	private SysRoleMenuMapper sysRoleMenuMapper;

	@Autowired
	private SysRoleOrgMapper sysRoleOrgMapper;

	/**
	 * 分页查询角色列表
	 * @param params
	 * @return
	 */
	@Override
	public Page<SysRoleEntity> listRole(Map<String, Object> params) {
		Query query = new Query(params);
		Page<SysRoleEntity> page = new Page<>(query);
		sysRoleMapper.listForPage(page, query);
		return page;
	}

	/**
	 * 新增角色
	 * @param role
	 * @return
	 */
	@Override
	public R saveRole(SysRoleEntity role) {
		int count = sysRoleMapper.save(role);
		return CommonUtils.msg(count);
	}

	/**
	 * 根据id查询角色
	 * @param id
	 * @return
	 */
	@Override
	public R getRoleById(Long id) {
		SysRoleEntity role = sysRoleMapper.getObjectById(id);
		List<Long> menuId = sysRoleMenuMapper.listMenuId(id);
		List<Long> orgId = sysRoleOrgMapper.listOrgId(id);
		role.setMenuIdList(menuId);
		role.setOrgIdList(orgId);
		return CommonUtils.msg(role);
	}

	/**
	 * 修改角色
	 * @param role
	 * @return
	 */
	@Override
	public R updateRole(SysRoleEntity role) {
		int count = sysRoleMapper.update(role);
		return CommonUtils.msg(count);
	}

	/**
	 * 删除角色
	 * @param id
	 * @return
	 */
	@Override
	public R batchRemove(Long[] id) {
		int count = sysRoleMapper.batchRemove(id);
		sysUserRoleMapper.batchRemoveByRoleId(id);
		sysRoleMenuMapper.batchRemoveByRoleId(id);
		sysRoleOrgMapper.batchRemoveByRoleId(id);
		return CommonUtils.msg(id, count);
	}

	/**
	 * 所有角色集合：用户角色选择数据源
	 * @return
	 */
	@Override
	public List<SysRoleEntity> listRole() {
		return sysRoleMapper.list();
	}

	/**
	 * 操作权限
	 * @param role
	 * @return
	 */
	@Override
	public R updateRoleOptAuthorization(SysRoleEntity role) {
		Long roleId = role.getRoleId();
		int count = sysRoleMenuMapper.remove(roleId);
		Query query = new Query();
		query.put("roleId", roleId);
		List<Long> menuId = role.getMenuIdList();
		if(menuId.size() > 0) {
			query.put("menuIdList", role.getMenuIdList());
			count = sysRoleMenuMapper.save(query);
		}
		return CommonUtils.msg(count);
	}

	/**
	 * 数据权限
	 * @param role
	 * @return
	 */
	@Override
	public R updateRoleDataAuthorization(SysRoleEntity role) {
		Long roleId = role.getRoleId();
		int count = sysRoleOrgMapper.remove(roleId);
		Query query = new Query();
		query.put("roleId", roleId);
		List<Long> orgId = role.getOrgIdList();
		if(orgId.size() > 0) {
			query.put("orgIdList", role.getOrgIdList());
			count = sysRoleOrgMapper.save(query);
		}
		return CommonUtils.msg(count);
	}
	
}

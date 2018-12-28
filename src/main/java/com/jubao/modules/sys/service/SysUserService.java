package com.jubao.modules.sys.service;

import com.jubao.common.entity.Page;
import com.jubao.common.entity.R;
import com.jubao.modules.sys.entity.SysUserEntity;
import com.jubao.modules.sys.entity.SysUserTokenEntity;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 系统用户
 * 
 */
public interface SysUserService {

	/**
	 * 分页查询用户列表
	 * @param params
	 * @return
	 */
	Page<SysUserEntity> listUser(Map<String, Object> params);

	/**
	 * 新增用户
	 * @param user
	 * @return
	 */
	R saveUser(SysUserEntity user);

	/**
	 * 根据id查询用户
	 * @param userId
	 * @return
	 */
	R getUserById(Long userId);

	/**
	 * 修改用户
	 * @param user
	 * @return
	 */
	R updateUser(SysUserEntity user);

	/**
	 * 删除用户
	 * @param id
	 * @return
	 */
	R batchRemove(Long[] id);

	/**
	 * 查询用户权限集合
	 * @param userId
	 * @return
	 */
	Set<String> listUserPerms(Long userId);

	/**
	 * 查询用户角色集合
	 * @param userId
	 * @return
	 */
	Set<String> listUserRoles(Long userId);

	/**
	 * 用户修改密码
	 * @param user
	 * @return
	 */
	R updatePswdByUser(SysUserEntity user);

	/**
	 * 启用用户
	 * @param id
	 * @return
	 */
	R updateUserEnable(Long[] id);

	/**
	 * 禁用用户
	 * @param id
	 * @return
	 */
	R updateUserDisable(Long[] id);

	/**
	 * 重置用户密码
	 * @param user
	 * @return
	 */
	R updatePswd(SysUserEntity user);

	/**
	 * 根据用户名查询用户信息
	 * @param username
	 * @return
	 */
	SysUserEntity getByUserName(String username);

	/**
	 * 用户所有机构id
	 * @param userId
	 * @return
	 */
	List<Long> listAllOrgId(Long userId);

	/**
	 * 保存用户token
	 * @param userId
	 * @return
	 */
	int saveOrUpdateToken(Long userId, String token);

	/**
	 * 根据token查询
	 * @param token
	 * @return
	 */
	SysUserTokenEntity getUserTokenByToken(String token);

	/**
	 * 根据userId查询
	 * @param userId
	 * @return
	 */
	SysUserTokenEntity getUserTokenByUserId(Long userId);

	/**
	 * 根据userId查询：用于token校验
	 * @param userId
	 * @return
	 */
	SysUserEntity getUserByIdForToken(Long userId);
	
}

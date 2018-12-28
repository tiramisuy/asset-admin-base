package com.jubao.modules.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import com.jubao.common.constant.RestApiConstant;
import com.jubao.common.entity.R;
import com.jubao.common.utils.JwtUtils;
import com.jubao.common.utils.MD5Utils;
import com.jubao.common.utils.TokenUtils;
import com.jubao.modules.sys.controller.AbstractController;
import com.jubao.modules.sys.entity.SysUserEntity;
import com.jubao.modules.sys.entity.SysUserTokenEntity;
import com.jubao.modules.sys.service.SysUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * rest授权controller
 * 
 */
@Api(value = "用户授权", description = "用户授权")
@RestController
public class RestAuthController extends AbstractController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private JwtUtils jwtUtils;

    /**
     * 登录授权校验
     * @return
     */
    @ApiOperation(value = "登录")
    @ApiImplicitParam(name = "token", value = "授权码")
    @RequestMapping(value = RestApiConstant.AUTH_REQUEST, method = {RequestMethod.GET, RequestMethod.POST})
    public R auth(@ApiParam(name = "username", value = "用户名") @RequestParam String username,
                  @ApiParam(name = "password", value = "密码") @RequestParam String password) {
        // 用户名为空
        if (StringUtils.isBlank(username.trim())) {
            return RestApiConstant.TokenErrorEnum.USER_USERNAME_NULL.getResp();
        }
        // 密码为空
        if (StringUtils.isBlank(password.trim())) {
            return RestApiConstant.TokenErrorEnum.USER_PASSWORD_NULL.getResp();
        }
        // 用户名不存在
        SysUserEntity sysUserEntity = sysUserService.getByUserName(username);
        if (sysUserEntity == null) {
            return RestApiConstant.TokenErrorEnum.USER_USERNAME_INVALID.getResp();
        }
        // 密码错误
        String checkPassword = MD5Utils.encrypt(username, password);
        if (!sysUserEntity.getPassword().equals(checkPassword)) {
            return RestApiConstant.TokenErrorEnum.USER_PASSWORD_INVALID.getResp();
        }
        // 用户被锁定
        if (sysUserEntity.getStatus() == 0) {
            return RestApiConstant.TokenErrorEnum.USER_DISABLE.getResp();
        }
        // 保存或者更新token
        String randomKey = TokenUtils.generateValue();
        String token = jwtUtils.generateToken(sysUserEntity.getUsername(),
                String.valueOf(sysUserEntity.getUserId()), randomKey);
        int count = sysUserService.saveOrUpdateToken(sysUserEntity.getUserId(), randomKey);
        if (count > 0) {
            R success = RestApiConstant.TokenErrorEnum.TOKEN_ENABLE.getResp();
            success.put(RestApiConstant.AUTH_TOKEN, token);
            return success;
        }
        return RestApiConstant.TokenErrorEnum.USER_AUTH_ERROR.getResp();
    }

    /**
     * 异步校验token，用于接口异步校验登录状态
     * @return
     */
    @ApiOperation(value = "校验token是否可用")
    @RequestMapping(value = RestApiConstant.AUTH_CHECK, method = {RequestMethod.GET, RequestMethod.POST})
    public R authStatus(@ApiParam(name = "token", value = "授权码") @RequestParam String token) {

        // token为空
        if (StringUtils.isBlank(token.trim())) {
            return RestApiConstant.TokenErrorEnum.TOKEN_NOT_FOUND.getResp();
        }

        // jwt过期时间校验
        if (jwtUtils.isExpred(token)) {
            return RestApiConstant.TokenErrorEnum.TOKEN_EXPIRED.getResp();
        }

        // 根据md5混淆字符串查询用户token
        SysUserTokenEntity sysUserTokenEntity = sysUserService.getUserTokenByToken(jwtUtils.getMd5Key(token));

        // 无效的token：token不存在
        if (sysUserTokenEntity == null) {
            return RestApiConstant.TokenErrorEnum.TOKEN_INVALID.getResp();
        }

        // token中的userId和数据库中userId是否一致
        if (sysUserTokenEntity.getUserId() != Long.parseLong(jwtUtils.getUserId(token))) {
            return RestApiConstant.TokenErrorEnum.TOKEN_INVALID.getResp();
        }

        // 无效token：用户不存在
        SysUserEntity sysUserEntity = sysUserService.getUserByIdForToken(sysUserTokenEntity.getUserId());
        if (sysUserEntity == null) {
            return RestApiConstant.TokenErrorEnum.TOKEN_INVALID.getResp();
        }

        // token过期:采用服务端时间校验
        if (TokenUtils.isExpired(sysUserTokenEntity.getGmtExpire())) {
            return RestApiConstant.TokenErrorEnum.TOKEN_EXPIRED.getResp();
        }

        // 用户是否禁用
        if (sysUserEntity.getStatus() == 0) {
            return RestApiConstant.TokenErrorEnum.USER_DISABLE.getResp();
        }

        return RestApiConstant.TokenErrorEnum.TOKEN_ENABLE.getResp();
    }

}

package com.jubao.common.constant;

import com.jubao.common.entity.R;

/**
 * rest模块常量
 * 
 */
public class RestApiConstant {

    /** 请求验证地址 **/
    public static final String AUTH_REQUEST = "/rest/auth";

    /** token有效期校验请求 **/
    public static final String AUTH_CHECK = "/rest/authStatus";

    /** 授权标识 **/
    public static final String AUTH_TOKEN = "token";

    /** token过期时间：默认7天 **/
    public static Long TOKEN_EXPIRE = 604800000L;

    /**
     * token错误提示枚举
     */
    public enum TokenErrorEnum {
        TOKEN_ENABLE(200, "验证成功"),
        TOKEN_NOT_FOUND(1000, "验证失败：获取token为空"),
        TOKEN_INVALID(1001, "验证失败：无效的token"),
        TOKEN_EXPIRED(1002, "验证失败：过期的token"),
        USER_USERNAME_NULL(1003, "验证失败：用户名为空"),
        USER_PASSWORD_NULL(1004, "验证失败：密码为空"),
        USER_USERNAME_INVALID(1005, "验证失败：用户名不存在"),
        USER_PASSWORD_INVALID(1006, "验证失败：密码错误"),
        USER_DISABLE(1007, "验证失败：用户被禁用，请联系管理员"),
        USER_AUTH_ERROR(1008, "验证失败：登录服务暂不可用");

        private int code;

        private String msg;

        TokenErrorEnum(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public R getResp() {
            return R.error(code, msg);
        }

    }

}

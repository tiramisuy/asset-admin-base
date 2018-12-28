package com.jubao.common.support.properties;

import org.apache.commons.lang.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 系统全局配置
 * 
 */
@Component
@ConfigurationProperties(prefix = GlobalProperties.PREFIX)
public class GlobalProperties {

    static final String PREFIX = "global";

    /** 文件上传目录 **/
    private String uploadLocation;

    /** 文件上传目录访问路径 **/
    private String uploadMapping;

    /** 是否开启redis会话管理器 **/
    private boolean redisSessionDao;

    /** 是否开启验证码 **/
    private boolean kaptchaEnable;

    /**
     * beetl全局变量
     * @return
     */
    public Map<String, Object> getBeetlGlobalVars() {
        Map<String, Object> vars = new HashMap<>(1);
        vars.put("kaptchaEnable", kaptchaEnable);
        return vars;
    }

    /**
     * WebConfig注册上传路径
     * @return
     */
    public String getRegisterUploadLocation() {
        if (StringUtils.isEmpty(uploadLocation)) {
            return null;
        }
        return "file:".concat(uploadLocation);
    }

    /**
     * WebConfig注册访问路径
     * @return
     */
    public String getRegisterUploadMapping() {
        if (StringUtils.isEmpty(uploadMapping)) {
            return null;
        }
        return uploadMapping.concat("**");
    }

    public String getUploadLocation() {
        return uploadLocation;
    }

    public void setUploadLocation(String uploadLocation) {
        this.uploadLocation = uploadLocation;
    }

    public String getUploadMapping() {
        return uploadMapping;
    }

    public void setUploadMapping(String uploadMapping) {
        this.uploadMapping = uploadMapping;
    }

    public boolean isRedisSessionDao() {
        return redisSessionDao;
    }

    public void setRedisSessionDao(boolean redisSessionDao) {
        this.redisSessionDao = redisSessionDao;
    }

    public boolean isKaptchaEnable() {
        return kaptchaEnable;
    }

    public void setKaptchaEnable(boolean kaptchaEnable) {
        this.kaptchaEnable = kaptchaEnable;
    }

}

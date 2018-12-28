package com.jubao.common.support.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * jwt属性配置
 */
@Configuration
@ConfigurationProperties(prefix = JwtProperties.PREFIX)
public class JwtProperties {

    static final String PREFIX = "jwt";

    /**
     * jwt密钥
     */
    private String secret = "dp";

    /**
     * 过期时间：604800 s（单位：秒）
     */
    private Long expiration = 604800L;

    /**
     * md5加密混淆key
     */
    private String md5Key = "randomKey";

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Long getExpiration() {
        return expiration;
    }

    public void setExpiration(Long expiration) {
        this.expiration = expiration;
    }

    public String getMd5Key() {
        return md5Key;
    }

    public void setMd5Key(String md5Key) {
        this.md5Key = md5Key;
    }

}

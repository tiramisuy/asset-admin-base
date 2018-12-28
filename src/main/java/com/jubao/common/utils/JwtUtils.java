package com.jubao.common.utils;

import io.jsonwebtoken.*;
import com.jubao.common.support.properties.JwtProperties;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * jwt工具类
 */
@Component
public class JwtUtils {

    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 获取用户名
     * @param token
     * @return
     */
    public String getUsername(String token) {
        return getClaims(token).getSubject();
    }

    /**
     * 创建时间
     * @param token
     * @return
     */
    public Date getCreateDate(String token) {
        return getClaims(token).getIssuedAt();
    }

    /**
     * 过期时间
     * @param token
     * @return
     */
    public Date getExpireDate(String token) {
        return getClaims(token).getExpiration();
    }

    /**
     * token接收者
     * @param token
     * @return
     */
    public String getUserId(String token) {
        return getClaims(token).getAudience();
    }

    /**
     * 获取md5混淆的key
     * @param token
     * @return
     */
    public String getMd5Key(String token) {
        return getPrivateClaims(token, jwtProperties.getMd5Key());
    }

    /**
     * 获取私有的claims
     * @param token
     * @param key
     * @return
     */
    private String getPrivateClaims(String token, String key) {
        return getClaims(token).get(key).toString();
    }

    /**
     * 获取payload
     * @param token
     * @return
     */
    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSecretByteArr())
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 解析token是否正确
     * @param token
     * @throws JwtException
     */
    public void parseToken(String token) throws JwtException {
        Jwts.parser().setSigningKey(getSecretByteArr()).parseClaimsJws(token).getBody();
    }

    /**
     * 验证token是否过期
     * @param token
     * @return
     */
    public boolean isExpred(String token) {
        try {
            final Date expireDate = getExpireDate(token);
            return expireDate.before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

    /**
     * 生成token，用户名
     * @param username
     * @return
     */
    public String generateToken(String username, String userId, String randomKey) {
        Map<String, Object> claims = new HashMap<>(1);
        claims.put(jwtProperties.getMd5Key(), randomKey);
        return generateToken(claims, username, userId);
    }

    /**
     * 生成token
     * @param claims
     * @param username 用户名
     * @param userId 用户id
     * @return
     */
    public String generateToken(Map<String, Object> claims, String username, String userId) {
        final Date currDate = new Date();
        final Date expireDate = new Date(currDate.getTime() + jwtProperties.getExpiration() * 1000);
        return Jwts.builder()
                .setClaims(claims)
                .setAudience(userId)
                .setSubject(username)
                .setIssuedAt(currDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS256, getSecretByteArr())
                .compact();
    }

    /**
     * 密钥base64数组
     * @return
     */
    private byte[] getSecretByteArr() {
        return Base64.decodeBase64(jwtProperties.getSecret());
    }

}

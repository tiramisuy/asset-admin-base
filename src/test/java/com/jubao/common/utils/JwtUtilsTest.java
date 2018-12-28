package com.jubao.common.utils;

import com.jubao.common.support.properties.JwtProperties;
import com.jubao.common.utils.JwtUtils;
import com.jubao.common.utils.TokenUtils;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * JwtUtilsTest
 */
public class JwtUtilsTest {

    private static final Logger log = LoggerFactory.getLogger(JwtUtilsTest.class);

    @InjectMocks
    JwtUtils jwtUtils = new JwtUtils();

    @Mock
    private JwtProperties jwtProperties;

    @BeforeTest
    public void init() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(jwtProperties.getSecret()).thenReturn("dp");
        Mockito.when(jwtProperties.getExpiration()).thenReturn(604800L);
        Mockito.when(jwtProperties.getMd5Key()).thenReturn("randomKey");
    }

    @Test
    public void test() {
        String username = "admin", userId = "111", randomKey = TokenUtils.generateValue();
        String token = jwtUtils.generateToken(username, userId, randomKey);
        log.info("用户名：{},生成token：{}", username, token);

        // 用户名
        String jwtSubject = jwtUtils.getUsername(token);
        Assert.assertEquals(jwtSubject, username);

        // 用户id
        String jwtUserId = jwtUtils.getUserId(token);
        Assert.assertEquals(jwtUserId, userId);

        // 混淆密钥
        String jwtRandomKey = jwtUtils.getMd5Key(token);
        Assert.assertEquals(jwtRandomKey, randomKey);

    }

}

package com.jubao.modules.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import com.jubao.common.annotation.RestAnon;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试接口
 * 
 */
@Api(value = "测试接口", description = "测试接口")
@RestController
@RequestMapping("/rest")
public class RestTestController {

    /**
     * 验证拦截
     * @return
     */
    @ApiOperation(value = "测试接口")
    @RequestMapping(value = "/testAuth", method = RequestMethod.GET)
    public String test() {
        return "auth token";
    }

    /**
     * 匿名调用：@RestAnon
     * @return
     */
    @ApiOperation(value = "匿名访问接口")
    @ApiImplicitParam(name = "token", value = "授权码")
    @RequestMapping(value = "/testAnon", method = RequestMethod.GET)
    @RestAnon
    public String testAnon() {
        return "rest anon";
    }

}

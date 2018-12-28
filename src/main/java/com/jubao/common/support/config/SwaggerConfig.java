package com.jubao.common.support.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * swagger配置
 * 
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        Parameter token = new ParameterBuilder()
                .name("token")
                .description("授权码")
                .required(true)
                .order(1)
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .build();
        List<Parameter> globalParams = new ArrayList<>(1);
        globalParams.add(token);
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(globalParams);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Dp Rest Api Doc")
                .description("Gp Api 文档")
                .termsOfServiceUrl("https://gitee.com/dp_group")
                .contact(new Contact("jubao", "www.jubaoqiandai.com", "363217237@qq.com"))
                .version("1.0.0")
                .build();
    }

}

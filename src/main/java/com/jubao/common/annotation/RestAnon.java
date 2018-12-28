package com.jubao.common.annotation;

import java.lang.annotation.*;

/**
 * rest接口不需要授权注解
 * 
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RestAnon {
}

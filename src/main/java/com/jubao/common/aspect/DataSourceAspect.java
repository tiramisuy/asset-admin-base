package com.jubao.common.aspect;

import com.jubao.common.annotation.DataSource;
import com.jubao.common.support.orm.db.DataSourceEnum;
import com.jubao.common.support.orm.db.DynamicDataSource;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 数据源切面处理
 * 
 */
@Aspect
@Component
public class DataSourceAspect {

    private static Logger LOG = LoggerFactory.getLogger(DataSourceAspect.class);

    @Pointcut("@annotation(com.jubao.common.annotation.DataSource)")
    public void pointCut() {

    }

    @Around("pointCut()")
    public Object doAround(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        DataSource ds = method.getAnnotation(DataSource.class);

        String dataSource = ds.value();
        if (StringUtils.isBlank(dataSource)) {
            DynamicDataSource.setDataSource(DataSourceEnum.MASTER.getName());
            LOG.debug("set datasource is null, use datasource : {}", dataSource);
        } else {
            DynamicDataSource.setDataSource(dataSource);
            LOG.debug("use datasource : {}", dataSource);
        }

        try {
            return point.proceed();
        } finally {
            DynamicDataSource.clearDataSource();
            LOG.debug("clear datasource...");
        }

    }

}

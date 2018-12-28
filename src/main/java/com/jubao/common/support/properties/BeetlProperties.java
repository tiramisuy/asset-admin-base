package com.jubao.common.support.properties;

import org.apache.commons.lang.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Properties;

/**
 * beetl配置
 * 
 */
@Configuration
@ConfigurationProperties(prefix = BeetlProperties.BEETLCONF_PREFIX)
@PropertySource("classpath:META-INF/application-default.properties")
public class BeetlProperties {

    /** 属性前缀名 **/
    static final String BEETLCONF_PREFIX = "beetl";

    /** 定界开始符号 **/
    private String delimiterStatementStart;

    /** 定界结束符号 **/
    private String delimiterStatementEnd;

    /** 自定义标签文件root目录 **/
    private String resourceTagRoot;

    /** 自定义标签文件后缀 **/
    private String resourceTagSuffix;

    /** 是否检测文件变化，开发使用true合适，生产使用false **/
    private String resourceAutoCheck;

    /** 视图解析器的路径前缀 **/
    private String prefix;

    public Properties getProperties(){
        Properties properties = new Properties();
        if(StringUtils.isNotEmpty(delimiterStatementStart)){
            if(delimiterStatementStart.startsWith("\\")){
                delimiterStatementStart = delimiterStatementStart.substring(1);
            }
            properties.setProperty("DELIMITER_STATEMENT_START",delimiterStatementStart);
        }
        if(StringUtils.isNotEmpty(delimiterStatementEnd)){
            properties.setProperty("DELIMITER_STATEMENT_END",delimiterStatementEnd);
        }else{
            properties.setProperty("DELIMITER_STATEMENT_END","null");
        }
        if(StringUtils.isNotEmpty(resourceTagRoot)){
            properties.setProperty("RESOURCE.tagRoot",resourceTagRoot);
        }
        if(StringUtils.isNotEmpty(resourceTagSuffix)){
            properties.setProperty("RESOURCE.tagSuffix",resourceTagSuffix);
        }
        if(StringUtils.isNotEmpty(resourceAutoCheck)){
            properties.setProperty("RESOURCE.autoCheck",resourceAutoCheck);
        }
        return properties;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getDelimiterStatementStart() {
        return delimiterStatementStart;
    }

    public void setDelimiterStatementStart(String delimiterStatementStart) {
        this.delimiterStatementStart = delimiterStatementStart;
    }

    public String getDelimiterStatementEnd() {
        return delimiterStatementEnd;
    }

    public void setDelimiterStatementEnd(String delimiterStatementEnd) {
        this.delimiterStatementEnd = delimiterStatementEnd;
    }

    public String getResourceTagRoot() {
        return resourceTagRoot;
    }

    public void setResourceTagRoot(String resourceTagRoot) {
        this.resourceTagRoot = resourceTagRoot;
    }

    public String getResourceTagSuffix() {
        return resourceTagSuffix;
    }

    public void setResourceTagSuffix(String resourceTagSuffix) {
        this.resourceTagSuffix = resourceTagSuffix;
    }

    public String getResourceAutoCheck() {
        return resourceAutoCheck;
    }

    public void setResourceAutoCheck(String resourceAutoCheck) {
        this.resourceAutoCheck = resourceAutoCheck;
    }
}

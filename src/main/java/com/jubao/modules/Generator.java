package com.jubao.modules;

import com.jubao.modules.sys.generator.JdbcGenUtils;

/**
 * 代码生成器
 * 
 */
public class Generator {

    public static void main(String[] args) throws Exception {

        String jdbcDriver = "com.mysql.jdbc.Driver";
        String jdbcUrl = "jdbc:mysql://192.168.10.254:3306/loan_asset?useUnicode=true&characterEncoding=utf-8";
        String jdbcUsername = "root";
        String jdbcPassword = "jubaodai";

        String tablePrefix = "gen_";

        String javaModule = "test";
        String webModule = "test";

        JdbcGenUtils.generatorCode(jdbcDriver, jdbcUrl, jdbcUsername, jdbcPassword, tablePrefix, javaModule, webModule);

    }

}

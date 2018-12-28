package com.jubao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * 应用启动器
 * 
 */
@SpringBootApplication
public class StartApplication extends SpringBootServletInitializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(StartApplication.class);

    /**
     * jar启动
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(StartApplication.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);
        LOGGER.info("The asset-admin application has been started successfully!");
    }

    /**
     * war启动
     * @param builder
     * @return
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        builder.bannerMode(Banner.Mode.OFF);
        SpringApplicationBuilder applicationBuilder = builder.sources(StartApplication.class);
        LOGGER.info("The asset-admin application has been started successfully!");
        return applicationBuilder;
    }

}

package com.yzz.lr;

import com.yzz.lr.mapper.SystemConfigMapper;
import com.yzz.lr.model.SystemConfig;
import com.yzz.lr.util.SystemConfigUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/12/20.
 */
@SpringBootApplication(exclude = { MongoAutoConfiguration.class, MongoDataAutoConfiguration.class })
public class Application extends SpringBootServletInitializer implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    @Autowired
    private SystemConfigMapper systemConfigMapper;



    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        builder.sources(this.getClass());
        return super.configure(builder);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

    }


    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        logger.info("启动成功,开始加载缓存");
        Map<String,Object> params = new HashMap<>();
        List<SystemConfig> list = systemConfigMapper.select(params);
        SystemConfigUtil.setConfig(list);
    }
}

package com.yzz.lr.util;

import com.yzz.lr.model.SystemConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/11/27.
 */
@Service
public class SystemConfigUtil {

    private static Map<String,String> config = new HashMap<>();

    private static final Logger logger = LoggerFactory.getLogger(SystemConfigUtil.class);

    public static void setConfig(List<SystemConfig> list) {
        for(SystemConfig systemConfig : list){
            config.put(systemConfig.getKey(),systemConfig.getValue());
        }
        logger.info("缓存加载成功");
    }

    public static void refresh(List<SystemConfig> list){
        config.clear();
        for(SystemConfig systemConfig : list){
            config.put(systemConfig.getKey(),systemConfig.getValue());
        }
    }

    public static String getValueByCode(String key){
        if(config.containsKey(key)){
            return config.get(key);
        }
        return "";
    }

}

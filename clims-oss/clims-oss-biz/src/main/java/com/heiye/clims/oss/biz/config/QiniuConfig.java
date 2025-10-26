package com.heiye.clims.oss.biz.config;

import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: heiye
 * @date: 2025/10/21 下午9:58
 * @version: v1.0.0
 * @description: 七牛云对象存储配置
 */
@Configuration
public class QiniuConfig {

    @Resource
    private QiniuProperties qiniuProperties;


    @Bean
    UploadManager uploadManager() {
        com.qiniu.storage.Configuration configuration = new com.qiniu.storage.Configuration(Region.huanan());

        UploadManager uploadManager = new UploadManager(configuration);
        return uploadManager;
    }

    @Bean
    public Auth auth() {
        return Auth.create(qiniuProperties.getAccessKey(), qiniuProperties.getSecretKey());
    }
}

package com.heiye.clims.oss.biz.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author: heiye
 * @date: 2025/10/21 下午9:59
 * @version: v1.0.0
 * @description: 七牛云对象存储配置
 */
@Slf4j
@Data
@Component
@ConfigurationProperties(prefix = "qiniu")
public class QiniuProperties {
    private String accessKey;

    private String secretKey;

    private String bucketName;

    private String domain;
}

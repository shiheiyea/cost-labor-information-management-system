package com.heiye.clims.oss.biz.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author: heiye
 * @date: 2024/11/26 下午9:44
 * @version: v1.0.0
 * @description: 阿里云 OSS 配置项
 */
@Data
@Component
@ConfigurationProperties(prefix = "aliyun-oss")
public class AliyunOSSProperties {
    private String endpoint;
    private String accessKey;
    private String secretKey;
    private String bucketName;
}

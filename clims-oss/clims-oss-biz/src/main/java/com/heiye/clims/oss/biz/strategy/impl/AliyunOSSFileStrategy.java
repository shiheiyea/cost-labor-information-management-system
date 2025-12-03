package com.heiye.clims.oss.biz.strategy.impl;

import com.aliyun.oss.OSS;
import com.heiye.clims.framework.common.exception.BizException;
import com.heiye.clims.oss.biz.config.AliyunOSSProperties;
import com.heiye.clims.oss.biz.enums.ResponseCodeEnum;
import com.heiye.clims.oss.biz.enums.StorageTypeEnum;
import com.heiye.clims.oss.biz.strategy.FileStrategy;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.UUID;

/**
 * @author: heiye
 * @date: 2024/11/21 下午1:33
 * @version: v1.0.0
 * @description: TODO
 */
@Slf4j
@Component
public class AliyunOSSFileStrategy implements FileStrategy {

    @Resource
    private AliyunOSSProperties aliyunOSSProperties;

    @Resource
    private OSS ossClient;

    /**
     * 获取存储类型枚举
     *
     * @return StorageTypeEnum
     */
    @Override
    public StorageTypeEnum getStorageType() {
        return StorageTypeEnum.Aliyun;
    }

    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    @Override
    @SneakyThrows
    public String uploadFile(MultipartFile file) {
        log.info("## 文件上传至阿里云 OSS ...");

        // 判断文件是否为空
        if (file == null || file.getSize() == 0) {
            log.error("==> 上传用户头像异常：文件大小为空 ...");
            throw new BizException(ResponseCodeEnum.FILE_SIZE_EMPTY);
        }

        // 文件的原始名称
        String originalFileName = file.getOriginalFilename();

        // 获取存储桶名称
        String bucketName = aliyunOSSProperties.getBucketName();

        // 生成存储对象的名称（将 UUID 字符串中的 - 替换成空字符串）
        String key = UUID.randomUUID().toString().replace("-", "");
        // 获取文件的后缀，如 .jpg
        String suffix = originalFileName.substring(originalFileName.lastIndexOf("."));

        // 拼接上文件后缀，即为要存储的文件名
        String objectName = String.format("%s%s", key, suffix);

        log.info("==> 开始上传文件至阿里云 OSS, ObjectName: {}", objectName);

        // 上传文件到阿里云 OSS
        ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(file.getInputStream().readAllBytes()));

        // 放回文件的访问链接
        String url = String.format("https://%s.%s/%s", bucketName, aliyunOSSProperties.getEndpoint(), objectName);
        log.info("==> 上传文件至阿里云 OSS 成功，访问路径: {}", url);

        return url;
    }
}

package com.heiye.clims.oss.biz.strategy.impl;

import com.heiye.clims.oss.biz.config.QiniuProperties;
import com.heiye.clims.oss.biz.enums.StorageTypeEnum;
import com.heiye.clims.oss.biz.strategy.FileStrategy;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.UUID;

/**
 * @author: heiye
 * @date: 2025/10/22 下午4:28
 * @version: v1.0.0
 * @description: 七牛云上传文件策略
 */
@Slf4j
@Component
public class QiniuFileStrategy implements FileStrategy {

    @Resource
    private UploadManager uploadManager;

    @Resource
    private Auth auth;

    @Resource
    private QiniuProperties qiniuProperties;

    /**
     * 获取存储类型枚举
     *
     * @return StorageTypeEnum
     */
    @Override
    public StorageTypeEnum getStorageType() {
        return StorageTypeEnum.Qiniu;
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
        log.info("## 上传文件至七牛云...");

        // 判断文件是否为空
        if (file == null || file.getSize() == 0) {
            log.error("==> 上传文件异常：文件大小为空 ...");
            throw new RuntimeException("文件大小不能为空");
        }

        // 文件的原始名称
        String originalFileName = file.getOriginalFilename();

        // 生成存储对象的名称（将 UUID 字符串中的 - 替换成空字符串）
        String key = UUID.randomUUID().toString().replace("-", "");
        // 获取文件的后缀，如 .jpg
        String suffix = originalFileName.substring(originalFileName.lastIndexOf("."));

        // 拼接上文件后缀，即为要存储的文件名
        String objectName = String.format("%s%s", key, suffix);

        // 生成上传凭证
        String uploadToken = auth.uploadToken(qiniuProperties.getBucketName());

        // 上传文件
        Response response = uploadManager.put(file.getInputStream().readAllBytes(), objectName, uploadToken);

        DefaultPutRet defaultPutRet = response.jsonToObject(DefaultPutRet.class);

        String url = String.format("%s/%s", qiniuProperties.getDomain(), defaultPutRet.key);

        log.info("==> 上传文件至七牛云成功，访问路径: {}", url);

        return url;
    }
}

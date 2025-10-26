package com.heiye.clims.oss.biz.strategy;

import com.heiye.clims.oss.biz.enums.StorageTypeEnum;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: heiye
 * @date: 2025/10/22 下午4:21
 * @version: v1.0.0
 * @description: 文件策略
 */
public interface FileStrategy {

    /**
     * 获取存储类型枚举
     *
     * @return StorageTypeEnum
     */
    StorageTypeEnum getStorageType();

    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    String uploadFile(MultipartFile file);
}

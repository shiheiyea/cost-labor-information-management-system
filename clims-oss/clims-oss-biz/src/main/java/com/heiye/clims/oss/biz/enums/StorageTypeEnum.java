package com.heiye.clims.oss.biz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: heiye
 * @date: 2025/10/22 下午4:22
 * @version: v1.0.0
 * @description: 对象存储类型枚举
 */
@Getter
@AllArgsConstructor
public enum StorageTypeEnum {
    Qiniu("qiniu"),
    Aliyun("aliyun"),
    Minio("minio"),
    ;

    private final String type;

    public static StorageTypeEnum getStorageType(String type) {
        for (StorageTypeEnum value : StorageTypeEnum.values()) {
            if (value.getType().equals(type)) {
                return value;
            }
        }
        return null;
    }
}

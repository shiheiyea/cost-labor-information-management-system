package com.heiye.clims.framework.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: heiye
 * @date: 2025/10/16 下午2:16
 * @version: v1.0.0
 * @description: 删除类型枚举
 */
@Getter
@AllArgsConstructor
public enum DeleteEnum {
    YES(true),
    NO(false);

    private final Boolean code;
}

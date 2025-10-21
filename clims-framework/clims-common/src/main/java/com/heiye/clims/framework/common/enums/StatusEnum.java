package com.heiye.clims.framework.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: heiye
 * @date: 2024/11/18 下午19:50
 * @version: v1.0.0
 * @description: 状态枚举
 */
@Getter
@AllArgsConstructor
public enum StatusEnum {
    // 启用
    ENABLE(0),
    // 禁用
    DISABLED(1);

    private final Integer value;
}

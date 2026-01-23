package com.heiye.clims.work.biz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: heiye
 * @date: 2025/11/02 下午4:32
 * @version: v1.0.0
 * @description: 工作状态枚举
 */
@Getter
@AllArgsConstructor
public enum WorkStatusEnum {

    // 没开始工作
    NOT_STARTED(0),
    // 工作中
    WORKING(1),
    // 正常完成工作
    COMPLETED(2),
    // 提前完成工作
    EARLY_COMPLETED(3),
    // 没手动结束工作但达到了工作时间
    TIME_OUT(4),
    ;

    private final Integer code;

    public static WorkStatusEnum getWorkStatusEnum(Integer code) {
        for (WorkStatusEnum value : values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }
}

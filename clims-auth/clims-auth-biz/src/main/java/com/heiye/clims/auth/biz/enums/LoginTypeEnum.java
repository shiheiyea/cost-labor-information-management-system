package com.heiye.clims.auth.biz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: heiye
 * @date: 2025/10/19 下午3:21
 * @version: v1.0.0
 * @description: 登录枚举类
 */
@Getter
@AllArgsConstructor
public enum LoginTypeEnum {
    PASSWORD(1),
    EMAIL_CODE(2),
    ;

    private final Integer type;

    public static LoginTypeEnum valueOf(Integer type) {
        for (LoginTypeEnum value : values()) {
            if (value.type.equals(type)) {
                return value;
            }
        }
        return null;
    }
}

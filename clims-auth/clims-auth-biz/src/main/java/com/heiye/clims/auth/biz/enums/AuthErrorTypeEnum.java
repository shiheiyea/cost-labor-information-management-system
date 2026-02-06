package com.heiye.clims.auth.biz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: heiye
 * @date: 2026/02/06 16:09
 * @version: v1.0.0
 * @description:
 */
@Getter
@AllArgsConstructor
public enum AuthErrorTypeEnum {
    // 未能从请求中读取到有效 token
    NOT_TOKEN("-1"),
    // 	已读取到 token，但是 token 无效
    INVALID_TOKEN("-2"),
    // 已读取到 token，但是 token 已经过期
    TOKEN_TIMEOUT("-3"),
    // 已读取到 token，但是 token 已被顶下线
    BE_REPLACED("-4"),
    // 已读取到 token，但是 token 已被踢下线
    KICK_OUT("-5"),
    // 已读取到 token，但是 token 已被冻结
    TOKEN_FREEZE("-6"),
    // 未按照指定前缀提交 token
    NO_PREFIX("-7")
    ;

    private final String type;

    public static AuthErrorTypeEnum getAuthErrorTypeEnum(String type) {
        for (AuthErrorTypeEnum value : values()) {
            if (value.type.equals(type)) {
                return value;
            }
        }
        return null;
    }
}

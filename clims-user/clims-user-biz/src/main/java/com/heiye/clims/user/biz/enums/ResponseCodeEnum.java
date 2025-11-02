package com.heiye.clims.user.biz.enums;

import com.heiye.clims.framework.common.exception.BaseExceptionInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: heiye
 * @date: 2025/10/06 下午5:57
 * @version: v1.0.0
 * @description:
 */
@Getter
@AllArgsConstructor
public enum ResponseCodeEnum implements BaseExceptionInterface {
    // ----------- 用户服务异常状态码 -----------
    UPDATE_USER_INFO_ERROR("USER-30000", "修改用户信息失败"),
    CANT_UPDATE_OTHER_USER_PROFILE("USER-30001", "您无权修改该用户信息"),
    DISPLAY_NAME_VALID_FAIL("USER-30002", "昵称格式错误"),
    USER_NOT_EXIST("USER-30003", "用户不存在")
    ;

    // 异常码
    private final String errorCode;
    // 错误信息
    private final String errorMessage;
}

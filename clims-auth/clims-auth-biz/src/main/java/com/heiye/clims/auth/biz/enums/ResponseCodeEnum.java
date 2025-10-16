package com.heiye.clims.auth.biz.enums;

import com.heiye.clims.common.exception.BaseExceptionInterface;
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
    // ----------- 通用异常状态码 -----------
    SYSTEM_ERROR("AUTH-10000", "出错啦，后台小哥正在努力修复中..."),
    PARAM_NOT_VALID("AUTH-10001", "参数错误"),

    // ----------- 业务异常状态码 -----------
    EMAIL_VERIFICATION_CODE_SEND_ERROR("AUTH-20000", "邮件发送错误"),
    VERIFICATION_CODE_SEND_FREQUENTLY("AUTH-20001", "请求太频繁，请3分钟后再试"),
    VERIFICATION_CODE_CHECK_ERROR("AUTH-20002", "您输入的验证码有误"),
    EMAIL_ALREADY_REGISTERED("AUTH-20003", "邮箱已被注册"),
    USERNAME_ALREADY_REGISTERED("AUTH-20004", "用户名已被注册"),
    ;

    // 异常码
    private final String errorCode;
    // 错误信息
    private final String errorMessage;
}

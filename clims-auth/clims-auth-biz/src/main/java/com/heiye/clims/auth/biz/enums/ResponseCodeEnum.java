package com.heiye.clims.auth.biz.enums;

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
    // ----------- 认证服务系统异常状态码 -----------
    AUTH_ERROR("AUTH-10000", "认证服务出现异常，请联系工作人员"),
    // ----------- 认证服务异常状态码 -----------
    EMAIL_VERIFICATION_CODE_SEND_ERROR("AUTH-20000", "邮件发送错误"),
    VERIFICATION_CODE_SEND_FREQUENTLY("AUTH-20001", "请求太频繁，请3分钟后再试"),
    VERIFICATION_CODE_CHECK_ERROR("AUTH-20002", "您输入的验证码有误"),
    EMAIL_ALREADY_REGISTERED("AUTH-20003", "邮箱已被注册"),
    USERNAME_ALREADY_REGISTERED("AUTH-20004", "用户名已被注册"),
    EMAIL_CHECK_USER_NOT_EXIST("AUTH-20005", "该邮箱还未被注册"),
    USER_NOT_EXIST("AUTH-20006", "用户不存在"),
    USER_PASSWORD_ERROR("AUTH-20007", "密码输入错误"),
    REGISTER_FAILED("AUTH-20008", "注册失败"),
    NOT_TOKEN("AUTH-20009", "请求中未携带 token"),
    INVALID_TOKEN("AUTH-20010", "token 无效"),
    TOKEN_TIMEOUT("AUTH-20011", "token 已经过期"),
    BE_REPLACED("AUTH-20012", "token 已被顶下线"),
    KICK_OUT("AUTH-20013", "token 已被踢下线"),
    TOKEN_FREEZE("AUTH-20014", "token 已被冻结"),
    NO_PREFIX("AUTH-20015", "未按照指定前缀提交 token")
    ;

    // 异常码
    private final String errorCode;
    // 错误信息
    private final String errorMessage;
}

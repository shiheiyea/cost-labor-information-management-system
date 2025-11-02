package com.heiye.clims.framework.common.enums;

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
    // ----------- 通用异常状态码 -----------
    SYSTEM_ERROR("SYSTEM-10000", "出错啦，后台小哥正在努力修复中..."),
    PARAM_NOT_VALID("SYSTEM-10001", "参数错误"),
    ;

    // 异常码
    private final String errorCode;
    // 错误信息
    private final String errorMessage;
}

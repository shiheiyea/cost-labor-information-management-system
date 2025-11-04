package com.heiye.clims.work.biz.enums;

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
    // ----------- 工作服务异常状态码 -----------
    WORK_NOT_EXIST("WORK-40000", "工作信息不存在"),
    ;

    // 异常码
    private final String errorCode;
    // 错误信息
    private final String errorMessage;
}

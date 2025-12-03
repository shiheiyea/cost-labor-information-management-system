package com.heiye.clims.oss.biz.enums;

import com.heiye.clims.framework.common.exception.BaseExceptionInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: heiye
 * @date: 2025/12/03 下午4:42
 * @version: v1.0.0
 * @description: 响应码枚举
 */
@Getter
@AllArgsConstructor
public enum ResponseCodeEnum implements BaseExceptionInterface {
    FILE_SIZE_EMPTY("400001", "文件大小不能为空")
    ;
    // 异常码
    private final String errorCode;
    // 错误信息
    private final String errorMessage;
}

package com.heiye.clims.framework.common.exception;

/**
 * @author: heiye
 * @date: 2024/11/18 下午18:21
 * @version: v1.0.0
 * @description: 业务异常接口
 */
public interface BaseExceptionInterface {

	// 获取异常码
    String getErrorCode();

	// 获取异常信息
    String getErrorMessage();
}


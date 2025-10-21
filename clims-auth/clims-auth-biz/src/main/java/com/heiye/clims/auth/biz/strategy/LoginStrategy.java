package com.heiye.clims.auth.biz.strategy;

import com.heiye.clims.auth.biz.enums.LoginTypeEnum;
import com.heiye.clims.auth.biz.model.vo.RegisterFinishRspVO;

/**
 * @author: heiye
 * @date: 2025/10/19 下午3:32
 * @version: v1.0.0
 * @description: 登录策略
 */
public interface LoginStrategy {
    /**
     * 获取登录类型枚举
     *
     * @return LoginTypeEnum
     */
    LoginTypeEnum getLoginType();

    RegisterFinishRspVO login(String identifier, String credential);
}

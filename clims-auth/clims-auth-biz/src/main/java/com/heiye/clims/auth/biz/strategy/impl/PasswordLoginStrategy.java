package com.heiye.clims.auth.biz.strategy.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.heiye.clims.auth.biz.enums.LoginTypeEnum;
import com.heiye.clims.auth.biz.enums.ResponseCodeEnum;
import com.heiye.clims.auth.biz.model.vo.RegisterFinishRspVO;
import com.heiye.clims.auth.biz.strategy.LoginStrategy;
import com.heiye.clims.framework.common.exception.BizException;
import com.heiye.clims.user.api.api.UserApi;
import com.heiye.clims.user.api.dto.FindUserByEmailReqDTO;
import com.heiye.clims.user.api.dto.FindUserByEmailRspDTO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author: heiye
 * @date: 2025/10/19 下午3:33
 * @version: v1.0.0
 * @description: 密码登录策略
 */
@Slf4j
@Component
public class PasswordLoginStrategy implements LoginStrategy {

    @Resource
    private UserApi userApi;

    /**
     * 获取登录类型枚举
     *
     * @return LoginTypeEnum
     */
    @Override
    public LoginTypeEnum getLoginType() {
        return LoginTypeEnum.PASSWORD;
    }

    @Override
    public RegisterFinishRspVO login(String identifier, String credential) {
        // 1. 查询用户信息
        FindUserByEmailReqDTO findUserByEmailReqDTO = FindUserByEmailReqDTO.builder()
                .email(identifier)
                .build();
        FindUserByEmailRspDTO findUserByEmailRspDTO = userApi.findUserByEmail(findUserByEmailReqDTO);

        // 用户不存在，抛出异常
        if (Objects.isNull(findUserByEmailRspDTO)) {
            throw new BizException(ResponseCodeEnum.USER_NOT_EXIST);
        }

        // 2. 判断密码是否一致，不一致抛出异常
        if (!Objects.equals(findUserByEmailRspDTO.getPassword(), credential)) {
            throw new BizException(ResponseCodeEnum.USER_PASSWORD_ERROR);
        }

        // 构建返参
        RegisterFinishRspVO registerFinishRspVO = RegisterFinishRspVO.builder()
                .userId(findUserByEmailRspDTO.getId())
                .finish(true)
                .token("")
                .build();

        // 3. 判断账号是否完成注册流程
        if (StringUtils.isBlank(findUserByEmailRspDTO.getNickname()) || StringUtils.isBlank(findUserByEmailRspDTO.getAvatar())) {
            // 用户未走完注册流程，需要重定向对应页面
            registerFinishRspVO.setFinish(false);
        }

        // 4. sa-token 登录
        StpUtil.login(findUserByEmailRspDTO.getId());

        // 5. 获取token
        String token = StpUtil.getTokenInfo().tokenValue;
        registerFinishRspVO.setToken(token);

        // 6. 返参
        return registerFinishRspVO;
    }
}

package com.heiye.clims.auth.biz.strategy.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.github.benmanes.caffeine.cache.Cache;
import com.heiye.clims.auth.biz.enums.LoginTypeEnum;
import com.heiye.clims.common.enums.ResponseCodeEnum;
import com.heiye.clims.auth.biz.model.vo.RegisterFinishRspVO;
import com.heiye.clims.auth.biz.strategy.LoginStrategy;
import com.heiye.clims.common.exception.BizException;
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
 * @date: 2025/10/19 下午3:37
 * @version: v1.0.0
 * @description: 邮箱登录策略
 */
@Slf4j
@Component
public class EmailLoginStrategy implements LoginStrategy {

    @Resource(name = "emailCaffeineCache")
    private Cache<String, String> emailCodeCache;

    @Resource
    private UserApi userApi;

    /**
     * 获取登录类型枚举
     *
     * @return LoginTypeEnum
     */
    @Override
    public LoginTypeEnum getLoginType() {
        return LoginTypeEnum.EMAIL_CODE;
    }

    @Override
    public RegisterFinishRspVO login(String identifier, String credential) {
        // 1. 验证码校验
        String localCode = emailCodeCache.getIfPresent(identifier);

        // 验证码输入错误抛出异常
        if (!Objects.equals(localCode, credential)) {
            throw new BizException(ResponseCodeEnum.VERIFICATION_CODE_CHECK_ERROR);
        }

        // 2. 查询对应邮箱账号信息 调用用户模块API
        FindUserByEmailReqDTO findUserByEmailReqDTO = FindUserByEmailReqDTO.builder()
                .email(identifier)
                .build();
        FindUserByEmailRspDTO findUserByEmailRspDTO = userApi.findUserByEmail(findUserByEmailReqDTO);

        // 用户不存在表面邮箱未被注册，抛出异常
        if (Objects.isNull(findUserByEmailRspDTO)) {
            throw new BizException(ResponseCodeEnum.EMAIL_CHECK_USER_NOT_EXIST);
        }

        // 构建返参
        RegisterFinishRspVO registerFinishRspVO = RegisterFinishRspVO.builder()
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

        // 4. 进行返参
        return registerFinishRspVO;
    }
}

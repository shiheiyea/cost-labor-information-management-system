package com.heiye.clims.auth.biz.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.github.benmanes.caffeine.cache.Cache;
import com.heiye.clims.auth.biz.enums.ResponseCodeEnum;
import com.heiye.clims.auth.biz.factory.LoginStrategyFactory;
import com.heiye.clims.auth.biz.model.vo.LoginReqVO;
import com.heiye.clims.auth.biz.model.vo.RegisterFinishRspVO;
import com.heiye.clims.auth.biz.strategy.LoginStrategy;
import com.heiye.clims.framework.common.exception.BizException;
import com.heiye.clims.framework.common.response.Response;
import com.heiye.clims.auth.biz.model.vo.RegisterReqVO;
import com.heiye.clims.auth.biz.service.AuthService;
import com.heiye.clims.framework.common.thread.LoginUserContextHolder;
import com.heiye.clims.user.api.api.UserApi;
import com.heiye.clims.user.api.dto.FindUserByEmailReqDTO;
import com.heiye.clims.user.api.dto.FindUserByEmailRspDTO;
import com.heiye.clims.user.api.dto.UserRegisterReqDTO;
import com.heiye.clims.user.api.dto.UserRegisterRspDTO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author: heiye
 * @date: 2025/09/19 下午9:53
 * @version: v1.0.0
 * @description: 认证服务
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Resource(name = "emailCaffeineCache")
    private Cache<String, String> emailCaffeineCache;

    @Resource
    private LoginStrategyFactory loginStrategyFactory;

    @Resource
    private UserApi userApi;

    /**
     * 注册
     *
     * @param registerReqVO
     * @return
     */
    @Override
    public Response<?> register(RegisterReqVO registerReqVO) {

        // 1. 验证码校验
        // 获取邮箱
        String email = registerReqVO.getEmail();
        // 获取验证码
        String code = registerReqVO.getCode();

        // 从本地缓存中获取验证码
        String localCode = emailCaffeineCache.getIfPresent(email);

        // 验证码输入错误抛出异常
        if (!Objects.equals(localCode, code)) {
            throw new BizException(ResponseCodeEnum.VERIFICATION_CODE_CHECK_ERROR);
        }

        // 2. 判断邮箱是否已注册 调用用户模块API
        FindUserByEmailReqDTO findUserByEmailReqDTO = FindUserByEmailReqDTO.builder()
                .email(email)
                .build();
        FindUserByEmailRspDTO findUserByEmailRspDTO = userApi.findUserByEmail(findUserByEmailReqDTO);

        // 邮箱已被注册抛出异常
        if (Objects.nonNull(findUserByEmailRspDTO)) {
            throw new BizException(ResponseCodeEnum.EMAIL_ALREADY_REGISTERED);
        }

        // 3. 创建用户 调用用户模块API
        UserRegisterReqDTO userRegisterReqDTO = UserRegisterReqDTO.builder()
                .email(email)
                .password(registerReqVO.getPassword())
                .build();

        // 创建用户
        UserRegisterRspDTO userRegisterRspDTO = userApi.register(userRegisterReqDTO);

        if (Objects.isNull(userRegisterRspDTO)) {
            throw new BizException(ResponseCodeEnum.REGISTER_FAILED);
        }

        // 获取用户ID
        Long userId = userRegisterRspDTO.getId();

        // 4. 删除缓存的验证码
        emailCaffeineCache.invalidate(email);

        // 5. sa-token 登录
        StpUtil.login(userId);

        // 6. 获取token
        String token = StpUtil.getTokenInfo().tokenValue;

        // 7. 返回结果
        RegisterFinishRspVO registerFinishRspVO = RegisterFinishRspVO.builder()
                .userId(userId)
                .finish(false)
                .token(token)
                .build();

        return Response.success(registerFinishRspVO);
    }

    /**
     * 登录
     *
     * @param loginReqVO
     * @return
     */
    @Override
    public Response<?> login(LoginReqVO loginReqVO) {
        // 获取登录策略
        LoginStrategy loginStrategy = loginStrategyFactory.getStrategy(loginReqVO.getType());
        // 登录
        RegisterFinishRspVO registerFinishRspVO = loginStrategy.login(loginReqVO.getIdentifier(), loginReqVO.getCredential());
        return Response.success(registerFinishRspVO);
    }

    /**
     * 登出
     *
     * @return
     */
    @Override
    public Response<?> logout() {
        // 获取当前登录用户 ID
        Long userId = LoginUserContextHolder.getUserId();

        // 登出
        StpUtil.logout(userId);
        return Response.success();
    }
}

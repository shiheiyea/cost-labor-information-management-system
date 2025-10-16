package com.heiye.clims.auth.biz.service.impl;

import com.github.benmanes.caffeine.cache.Cache;
import com.heiye.clims.auth.biz.domain.dos.UserDO;
import com.heiye.clims.auth.biz.domain.mapper.UserDOMapper;
import com.heiye.clims.auth.biz.enums.ResponseCodeEnum;
import com.heiye.clims.auth.biz.model.dto.RegisterCheckRspVO;
import com.heiye.clims.common.enums.DeleteEnum;
import com.heiye.clims.common.enums.StatusEnum;
import com.heiye.clims.common.exception.BizException;
import com.heiye.clims.common.response.Response;
import com.heiye.clims.auth.biz.model.vo.RegisterReqVO;
import com.heiye.clims.auth.biz.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author: heiye
 * @date: 2025/09/19 下午9:53
 * @version: v1.0.0
 * @description: 认证服务
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDOMapper userDOMapper;

    @Resource(name = "emailCaffeineCache")
    private Cache<String, String> emailCaffeineCache;

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
        // 获取用户名
        String username = registerReqVO.getUsername();
        // 获取验证码
        String code = registerReqVO.getCode();

        // 从本地缓存中获取验证码
        String localCode = emailCaffeineCache.getIfPresent(email);

        // 验证码输入错误抛出异常
        if (!Objects.equals(localCode, code)) {
            throw new BizException(ResponseCodeEnum.VERIFICATION_CODE_CHECK_ERROR);
        }

        // 2. 判断邮箱是否已注册 和 账号名是否被注册
        RegisterCheckRspVO registerCheckRspVO = userDOMapper.checkRegisterConflicts(username, email);

        // 邮箱已被注册抛出异常
        if (registerCheckRspVO.getEmailExist()) {
            throw new BizException(ResponseCodeEnum.EMAIL_ALREADY_REGISTERED);
        }

        // 账号名已被注册抛出异常
        if (registerCheckRspVO.getUserNameExist()) {
            throw new BizException(ResponseCodeEnum.USERNAME_ALREADY_REGISTERED);
        }

        // 3. 创建用户
        UserDO userDO = UserDO.builder()
                .avatar("")
                .userName(username)
                .nickname("")
                .email(email)
                .password(registerReqVO.getPassword())
                .status(StatusEnum.ENABLE.getValue())
                .isDeleted(DeleteEnum.NO.getCode())
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();

        userDOMapper.insert(userDO);

        // 4. 删除缓存的验证码
        emailCaffeineCache.invalidate(email);

        return Response.success();
    }
}

package com.heiye.clims.auth.biz.strategy.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.heiye.clims.auth.biz.domain.dos.UserDO;
import com.heiye.clims.auth.biz.domain.mapper.UserDOMapper;
import com.heiye.clims.auth.biz.enums.LoginTypeEnum;
import com.heiye.clims.auth.biz.enums.ResponseCodeEnum;
import com.heiye.clims.auth.biz.model.vo.RegisterFinishRspVO;
import com.heiye.clims.auth.biz.strategy.LoginStrategy;
import com.heiye.clims.common.exception.BizException;
import com.heiye.clims.common.util.ParamUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
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
    private final UserDOMapper userDOMapper;

    public PasswordLoginStrategy(UserDOMapper userDOMapper) {
        this.userDOMapper = userDOMapper;
    }

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
        // 1. 判断传入的凭证是邮箱还是用户名
        boolean checkEmail = ParamUtils.checkEmail(identifier);

        // 构建查询条件
        LambdaQueryWrapper<UserDO> userDOLambdaQueryWrapper = Wrappers.<UserDO>lambdaQuery();

        // 用户信息
        UserDO userDO;

        // 如果用户输入凭证是邮箱则拼接邮箱查询，否则查询用户名
        if (checkEmail) {
            userDO = userDOMapper.selectOne(userDOLambdaQueryWrapper.eq(UserDO::getEmail, identifier));
        } else {
            userDO = userDOMapper.selectOne(userDOLambdaQueryWrapper.eq(UserDO::getUserName, identifier));
        }

        // 用户不存在，抛出异常
        if (Objects.isNull(userDO)) {
            throw new BizException(ResponseCodeEnum.USER_NOT_EXIST);
        }

        // 3. 判断密码是否一致，不一致抛出异常
        if (!Objects.equals(userDO.getPassword(), credential)) {
            throw new BizException(ResponseCodeEnum.USER_PASSWORD_ERROR);
        }

        // 构建返参
        RegisterFinishRspVO registerFinishRspVO = RegisterFinishRspVO.builder()
                .finish(true)
                .token("")
                .build();

        // 4. 判断账号是否完成注册流程
        if (StringUtils.isBlank(userDO.getNickname()) || StringUtils.isBlank(userDO.getAvatar())) {
            // 用户未走完注册流程，需要重定向对应页面
            registerFinishRspVO.setFinish(false);
        }

        // 5. 返参
        return registerFinishRspVO;
    }
}

package com.heiye.clims.user.biz.service.impl;

import com.google.common.base.Preconditions;
import com.heiye.clims.framework.common.exception.BizException;
import com.heiye.clims.framework.common.response.Response;
import com.heiye.clims.framework.common.util.ParamUtils;
import com.heiye.clims.user.biz.domain.dos.UserDO;
import com.heiye.clims.user.biz.domain.mapper.UserDOMapper;
import com.heiye.clims.user.biz.enums.ResponseCodeEnum;
import com.heiye.clims.user.biz.model.FindUserProfileRspVO;
import com.heiye.clims.user.biz.model.UpdateUserInfoReqVO;
import com.heiye.clims.user.biz.service.UserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author: heiye
 * @date: 2025/10/21 下午12:52
 * @version: v1.0.0
 * @description: 用户业务
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDOMapper userDOMapper;

    /**
     * 修改用户信息
     *
     * @param updateUserInfoReqVO
     * @return
     */
    @Override
    public Response<?> updateUserInfo(UpdateUserInfoReqVO updateUserInfoReqVO) {
        // 获取用户 ID
        Long userId = updateUserInfoReqVO.getUserId();
        // 获取用户昵称
        String nickname = updateUserInfoReqVO.getNickname();
        // 获取用户头像
        String avatar = updateUserInfoReqVO.getAvatar();

        // TODO: 获取用户登录 ID
//        Long loginUserId = LoginUserContextHolder.getUserId();
        Long loginUserId = 1L;

        // 非号主本人，无法修改其个人信息
        if (!Objects.equals(loginUserId, userId)) {
            throw new BizException(ResponseCodeEnum.CANT_UPDATE_OTHER_USER_PROFILE);
        }

        // 创建用户 DO 对象
        UserDO userDO = new UserDO();

        // 设置当前需要更新的用户ID
        userDO.setId(userId);
        // 标识位：是否需要更新
        boolean needUpdate = false;

        // 昵称校验
        if (StringUtils.isNotBlank(nickname)) {
            Preconditions.checkArgument(ParamUtils.checkNickname(nickname), ResponseCodeEnum.DISPLAY_NAME_VALID_FAIL.getErrorMessage());
            userDO.setNickname(nickname);
            needUpdate = true;
        }

        // 头像校验
        if (StringUtils.isNotBlank(avatar)) {
            userDO.setAvatar(avatar);
            needUpdate = true;
        }

        if (needUpdate) {
            // 设置更新时间
            userDO.setUpdateTime(LocalDateTime.now());
            // 修改用户信息
            userDOMapper.updateById(userDO);
        }

        return Response.success();
    }

    /**
     * 查询用户主页信息
     *
     * @param userId
     * @return
     */
    @Override
    public Response<?> findUserProfile(Long userId) {
        // 查询用户信息
        UserDO userDO = userDOMapper.selectById(userId);

        // 用户不存在
        if (Objects.isNull(userDO)) {
            throw new BizException(ResponseCodeEnum.USER_NOT_EXIST);
        }

        // 构建返回结果
        FindUserProfileRspVO findUserProfileRspVO = FindUserProfileRspVO.builder()
                .nickname(userDO.getNickname())
                .avatar(userDO.getAvatar())
                .build();

        return Response.success(findUserProfileRspVO);
    }
}

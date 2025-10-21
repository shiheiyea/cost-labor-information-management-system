package com.heiye.clims.user.biz.service.impl;

import com.heiye.clims.common.enums.ResponseCodeEnum;
import com.heiye.clims.common.exception.BizException;
import com.heiye.clims.common.response.Response;
import com.heiye.clims.user.biz.domain.dos.UserDO;
import com.heiye.clims.user.biz.domain.mapper.UserDOMapper;
import com.heiye.clims.user.biz.model.UpdateUserInfoRepVO;
import com.heiye.clims.user.biz.service.UserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
     * @param updateUserInfoRepVO
     * @return
     */
    @Override
    public Response<?> updateUserInfo(UpdateUserInfoRepVO updateUserInfoRepVO) {
        // 获取用户昵称
        String nickname = updateUserInfoRepVO.getNickname();
        // 获取用户头像
        String avatar = updateUserInfoRepVO.getAvatar();

        // TODO: 获取用户登录 ID
        Long userId = 1L;

        // 创建用户 DO 对象
        UserDO userDO = UserDO.builder()
                .id(userId)
                .nickname(nickname)
                .avatar(avatar)
                .build();

        // 修改用户信息
        int count = userDOMapper.updateById(userDO);

        // 当 count <= 0 时，说明未修改到用户信息，抛出异常
        if (count <= 0) {
            throw new BizException(ResponseCodeEnum.UPDATE_USER_INFO_ERROR);
        }

        return Response.success();
    }
}

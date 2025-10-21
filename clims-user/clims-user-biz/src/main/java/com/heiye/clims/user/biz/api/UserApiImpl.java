package com.heiye.clims.user.biz.api;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.heiye.clims.common.enums.DeleteEnum;
import com.heiye.clims.common.enums.StatusEnum;
import com.heiye.clims.user.api.api.UserApi;
import com.heiye.clims.user.api.dto.FindUserByEmailReqDTO;
import com.heiye.clims.user.api.dto.FindUserByEmailRspDTO;
import com.heiye.clims.user.api.dto.UserRegisterReqDTO;
import com.heiye.clims.user.biz.domain.dos.UserDO;
import com.heiye.clims.user.biz.domain.mapper.UserDOMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author: heiye
 * @date: 2025/10/21 下午12:52
 * @version: v1.0.0
 * @description: 提供给其他模块的用户业务
 */
@Slf4j
@Service
@Validated
public class UserApiImpl implements UserApi {

    @Resource
    private UserDOMapper userDOMapper;

    /**
     * 根据邮箱查询用户信息
     *
     * @param findUserByEmailReqDTO
     * @return
     */
    @Override
    public FindUserByEmailRspDTO findUserByEmail(FindUserByEmailReqDTO findUserByEmailReqDTO) {

        // 获取邮箱
        String email = findUserByEmailReqDTO.getEmail();

        // 查询用户信息
        UserDO userDO = userDOMapper.selectOne(Wrappers.<UserDO>lambdaQuery()
                .eq(UserDO::getEmail, email)
                .eq(UserDO::getIsDeleted, DeleteEnum.NO));

        // 用户不存在
        if (Objects.isNull(userDO)) {
            return null;
        }

        // 构建返回结果
        return FindUserByEmailRspDTO.builder()
                .id(userDO.getId())
                .password(userDO.getPassword())
                .build();
    }

    /**
     * 注册
     *
     * @param userRegisterReqDTO
     * @return
     */
    @Override
    public void register(UserRegisterReqDTO userRegisterReqDTO) {
        // 创建用户
        UserDO userDO = UserDO.builder()
                .avatar("")
                .nickname("")
                .email(userRegisterReqDTO.getEmail())
                .password(userRegisterReqDTO.getPassword())
                .status(StatusEnum.ENABLE.getValue())
                .isDeleted(DeleteEnum.NO.getCode())
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();

        userDOMapper.insert(userDO);
    }
}

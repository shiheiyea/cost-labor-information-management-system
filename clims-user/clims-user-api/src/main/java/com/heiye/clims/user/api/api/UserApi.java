package com.heiye.clims.user.api.api;

import com.heiye.clims.common.response.Response;
import com.heiye.clims.user.api.dto.FindUserByEmailReqDTO;
import com.heiye.clims.user.api.dto.FindUserByEmailRspDTO;
import com.heiye.clims.user.api.dto.UserRegisterReqDTO;

/**
 * @author: heiye
 * @date: 2025/10/21 下午12:39
 * @version: v1.0.0
 * @description: 提供给其他模块调用用户模块接口
 */
public interface UserApi {
    /**
     * 根据邮箱查询用户信息
     *
     * @param findUserByEmailReqDTO
     * @return
     */
    FindUserByEmailRspDTO findUserByEmail(FindUserByEmailReqDTO findUserByEmailReqDTO);

    /**
     * 注册
     *
     * @param userRegisterReqDTO
     * @return
     */
    void register(UserRegisterReqDTO userRegisterReqDTO);
}

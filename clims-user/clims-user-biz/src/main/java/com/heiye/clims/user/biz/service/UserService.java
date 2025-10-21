package com.heiye.clims.user.biz.service;

import com.heiye.clims.framework.common.response.Response;
import com.heiye.clims.user.biz.model.UpdateUserInfoRepVO;

/**
 * @author: heiye
 * @date: 2025/10/21 下午12:52
 * @version: v1.0.0
 * @description: 用户业务
 */
public interface UserService {
    /**
     * 修改用户信息
     *
     * @param updateUserInfoRepVO
     * @return
     */
    Response<?> updateUserInfo(UpdateUserInfoRepVO updateUserInfoRepVO);
}

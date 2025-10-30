package com.heiye.clims.user.biz.service;

import com.heiye.clims.framework.common.response.Response;
import com.heiye.clims.user.biz.model.FindUserProfileReqVO;
import com.heiye.clims.user.biz.model.UpdateUserInfoReqVO;

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
     * @param updateUserInfoReqVO
     * @return
     */
    Response<?> updateUserInfo(UpdateUserInfoReqVO updateUserInfoReqVO);

    /**
     * 查询用户主页信息
     *
     * @param findUserProfileReqVO
     * @return
     */
    Response<?> findUserProfile(FindUserProfileReqVO findUserProfileReqVO);
}

package com.heiye.clims.auth.biz.service;

import com.heiye.clims.auth.biz.model.vo.LoginReqVO;
import com.heiye.clims.common.response.Response;
import com.heiye.clims.auth.biz.model.vo.RegisterReqVO;

/**
 * @author: heiye
 * @date: 2025/09/19 下午9:52
 * @version: v1.0.0
 * @description: 认证服务
 */
public interface AuthService {

    /**
     * 注册
     *
     * @param registerReqVO
     * @return
     */
    Response<?> register(RegisterReqVO registerReqVO);

    /**
     * 登录
     *
     * @param loginReqVO
     * @return
     */
    Response<?> login(LoginReqVO loginReqVO);
}

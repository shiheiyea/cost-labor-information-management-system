package com.heiye.auth.biz.service;

import com.example.costLaborInformationManagementSystem.common.response.Response;
import com.heiye.auth.biz.model.vo.RegisterReqVO;

/**
 * @author: heiye
 * @date: 2025/09/19 下午9:52
 * @version: v1.0.0
 * @description: 认证服务
 */
public interface UserService {

    /**
     * 注册
     *
     * @param registerReqVO
     * @return
     */
    Response<?> register(RegisterReqVO registerReqVO);
}

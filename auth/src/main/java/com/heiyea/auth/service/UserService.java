package com.heiyea.auth.service;

import com.heiyea.auth.model.vo.RegisterReqVO;
import com.example.costLaborInformationManagementSystem.common.response.Response;

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

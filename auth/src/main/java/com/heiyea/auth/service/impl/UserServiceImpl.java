package com.heiyea.auth.service.impl;

import com.heiyea.auth.model.vo.RegisterReqVO;
import com.heiyea.auth.service.UserService;
import com.example.costLaborInformationManagementSystem.common.response.Response;
import org.springframework.stereotype.Service;

/**
 * @author: heiye
 * @date: 2025/09/19 下午9:53
 * @version: v1.0.0
 * @description: 认证服务
 */
@Service
public class UserServiceImpl implements UserService {


    /**
     * 注册
     *
     * @param registerReqVO
     * @return
     */
    @Override
    public Response<?> register(RegisterReqVO registerReqVO) {
        return null;
    }
}

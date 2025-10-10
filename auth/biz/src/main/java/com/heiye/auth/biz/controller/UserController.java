package com.heiye.auth.biz.controller;

import com.example.costLaborInformationManagementSystem.common.response.Response;
import com.heiye.auth.biz.model.vo.RegisterReqVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author: heiye
 * @date: 2025/09/19 下午9:27
 * @version: v1.0.0
 * @description: 认证服务
 */
@Validated
@RestController
public class UserController {

    /**
     * 注册
     *
     * @param registerReqVO
     * @return
     */
    @PostMapping("/register")
    public Response<?> register(@RequestBody RegisterReqVO registerReqVO) {
        return null;
    }

}

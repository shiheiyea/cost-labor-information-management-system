package com.heiye.clims.auth.biz.controller;

import com.heiye.clims.auth.biz.model.vo.RegisterReqVO;
import com.heiye.clims.common.response.Response;
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

package com.heiye.clims.user.biz.controller;

import com.heiye.clims.framework.common.response.Response;
import com.heiye.clims.user.biz.model.UpdateUserInfoRepVO;
import com.heiye.clims.user.biz.service.UserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: heiye
 * @date: 2025/10/21 下午9:33
 * @version: v1.0.0
 * @description: 用户业务
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/user")
public class UserServiceController {

    @Resource
    private UserService userService;

    /**
     * 修改用户信息
     * @param updateUserInfoRepVO
     * @return
     */
    @PostMapping("/update")
    Response<?> updateUserInfo(UpdateUserInfoRepVO updateUserInfoRepVO) {
        return userService.updateUserInfo(updateUserInfoRepVO);
    }
}

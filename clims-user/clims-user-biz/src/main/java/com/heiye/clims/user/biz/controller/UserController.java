package com.heiye.clims.user.biz.controller;

import com.heiye.clims.framework.common.response.Response;
import com.heiye.clims.user.biz.model.UpdateUserInfoReqVO;
import com.heiye.clims.user.biz.service.UserService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 修改用户信息
     *
     * @param updateUserInfoReqVO
     * @return
     */
    @PostMapping("/update")
    Response<?> updateUserInfo(@RequestBody @Valid UpdateUserInfoReqVO updateUserInfoReqVO) {
        return userService.updateUserInfo(updateUserInfoReqVO);
    }

    /**
     * 查询用户主页信息
     *
     * @param userId
     * @return
     */
    @GetMapping("/profile")
    Response<?> findUserProfile(@RequestParam Long userId) {
        return userService.findUserProfile(userId);
    }
}

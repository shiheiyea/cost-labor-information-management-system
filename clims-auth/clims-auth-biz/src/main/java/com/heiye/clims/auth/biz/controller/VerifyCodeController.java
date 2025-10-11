package com.heiye.clims.auth.biz.controller;

import com.heiye.clims.auth.biz.model.vo.SendEmailVerificationCodeReqVO;
import com.heiye.clims.auth.biz.service.VerifyCodeService;
import com.heiye.clims.common.response.Response;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: heiye
 * @date: 2025/10/06 下午5:38
 * @version: v1.0.0
 * @description: 验证码接口
 */
@Validated
@RestController
public class VerifyCodeController {

    @Resource
    private VerifyCodeService verifyCodeService;

    /**
     * 发送邮箱验证码
     *
     * @param sendEmailVerificationCodeReqVO
     * @return
     */
    @PostMapping("/verify/code/send/email")
    public Response<?> sendEmailVerificationCode(@RequestBody @Valid SendEmailVerificationCodeReqVO sendEmailVerificationCodeReqVO) {
        return verifyCodeService.sendEmailVerificationCode(sendEmailVerificationCodeReqVO);
    }
}

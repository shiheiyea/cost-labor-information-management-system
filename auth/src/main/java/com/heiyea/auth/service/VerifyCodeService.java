package com.heiyea.auth.service;

import com.heiyea.auth.model.vo.SendEmailVerificationCodeReqVO;
import com.example.costLaborInformationManagementSystem.common.response.Response;

/**
 * @author: heiye
 * @date: 2025/10/06 下午5:41
 * @version: v1.0.0
 * @description: 验证码接口
 */
public interface VerifyCodeService {

    /**
     * 发送邮箱验证码
     *
     * @param sendEmailVerificationCodeReqVO
     * @return
     */
    Response<?> sendEmailVerificationCode(SendEmailVerificationCodeReqVO sendEmailVerificationCodeReqVO);
}

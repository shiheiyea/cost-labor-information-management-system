package com.heiye.auth.biz.service;

import com.example.costLaborInformationManagementSystem.common.response.Response;
import com.heiye.auth.biz.model.vo.SendEmailVerificationCodeReqVO;

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

package com.heiye.clims.auth.biz.model.vo;

import com.heiye.clims.common.util.ParamUtils;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: heiye
 * @date: 2025/02/21 下午7:35
 * @version: v1.0.0
 * @description: 发送邮箱验证码
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SendEmailVerificationCodeReqVO {
    /**
     * 邮箱
     */
    @NotEmpty(message = "邮箱不能为空")
    @Pattern(regexp = ParamUtils.EMAIL_REGEX, message = "邮箱格式错误")
    private String email;
}

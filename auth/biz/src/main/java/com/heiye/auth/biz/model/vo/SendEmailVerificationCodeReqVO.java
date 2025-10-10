package com.heiye.auth.biz.model.vo;

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
 * @description: 邮箱验证
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
    @Pattern(regexp = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+", message = "邮箱格式错误")
    private String email;
}

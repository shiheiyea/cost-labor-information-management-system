package com.heiye.clims.user.api.dto;

import com.heiye.clims.common.util.ParamUtils;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: heiye
 * @date: 2025/10/21 下午12:44
 * @version: v1.0.0
 * @description: 根据邮箱查询用户信息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FindUserByEmailReqDTO {
    @NotEmpty(message = "邮箱不能为空")
    @Pattern(regexp = ParamUtils.EMAIL_REGEX, message = "邮箱格式错误")
    private String email;
}

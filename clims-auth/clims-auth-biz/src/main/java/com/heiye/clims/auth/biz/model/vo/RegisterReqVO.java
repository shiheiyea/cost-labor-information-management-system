package com.heiye.clims.auth.biz.model.vo;

import com.heiye.clims.common.util.ParamUtils;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

/**
 * @author: heiye
 * @date: 2025/09/19 下午9:33
 * @version: v1.0.0
 * @description: 注册
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterReqVO {
    /**
     * 账号
     */
    @NotEmpty(message = "账号不能为空")
    @Length(min = 4, max = 16, message = "账号长度为 4-16 位")
    @Pattern(regexp = ParamUtils.USER_NAME_REGEX, message = "用户名只能包含字母和数字")
    private String username;

    /**
     * 密码
     */
    @NotEmpty(message = "密码不能为空")
    @Length(min = 8, max = 20, message = "密码长度为 4-16 位")
    @Pattern(regexp = ParamUtils.PASSWORD_REGEX, message = "请让密码至少1个大写字母至少1个小写、至少1个数字和至少1个特殊字符")
    private String password;

    /**
     * 邮箱
     */
    @NotEmpty(message = "邮箱不能为空")
    @Pattern(regexp = ParamUtils.EMAIL_REGEX, message = "邮箱格式错误")
    private String email;

    /**
     * 邮箱验证码
     */
    @NotEmpty(message = "邮箱验证码不能为空")
    private String code;
}

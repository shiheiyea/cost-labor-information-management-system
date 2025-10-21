package com.heiye.clims.auth.biz.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: heiye
 * @date: 2025/10/16 下午2:38
 * @version: v1.0.0
 * @description: 登录
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginReqVO {
    /**
     * 登录标识
     */
    private String identifier;
    /**
     * 登录凭证
     */
    private String credential;
    /**
     * 登录类型
     * 1: 密码 2: 邮箱验证码
     */
    private Integer type;
}

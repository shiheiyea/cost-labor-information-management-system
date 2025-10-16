package com.heiye.clims.auth.biz.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: heiye
 * @date: 2025/10/16 下午1:51
 * @version: v1.0.0
 * @description: 注册查询dto
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterCheckRspVO {
    /**
     * 邮箱是否存在
     */
    private Boolean emailExist;

    /**
     * 账号是否存在
     */
    private Boolean userNameExist;
}

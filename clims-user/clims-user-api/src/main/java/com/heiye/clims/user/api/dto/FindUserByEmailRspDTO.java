package com.heiye.clims.user.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: heiye
 * @date: 2025/10/21 下午12:48
 * @version: v1.0.0
 * @description: 根据邮箱查询用户信息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FindUserByEmailRspDTO {
    private Long id;

    private String password;

    private String nickname;

    private String avatar;
}

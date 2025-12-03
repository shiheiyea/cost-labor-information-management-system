package com.heiye.clims.user.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: heiye
 * @date: 2025/12/03 下午5:37
 * @version: v1.0.0
 * @description: 注册返回结果
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterRspDTO {
    private Long id;
}

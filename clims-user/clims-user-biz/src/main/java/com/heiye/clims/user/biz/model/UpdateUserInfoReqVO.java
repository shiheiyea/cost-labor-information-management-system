package com.heiye.clims.user.biz.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * @author: heiye
 * @date: 2025/10/21 下午1:34
 * @version: v1.0.0
 * @description: 修改用户信息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserInfoReqVO {
    @NotNull(message = "用户 ID 不能为空")
    private Long userId;

    @NotEmpty(message = "昵称不能为空")
    private String nickname;

    @NotEmpty(message = "头像不能为空")
    private String avatar;
}

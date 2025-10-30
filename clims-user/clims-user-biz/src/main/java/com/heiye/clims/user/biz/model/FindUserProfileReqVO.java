package com.heiye.clims.user.biz.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: heiye
 * @date: 2025/10/30 下午2:25
 * @version: v1.0.0
 * @description: 查询用户主页信息
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FindUserProfileReqVO {
    /**
     * 用户 ID
     */
    private Long userId;
}

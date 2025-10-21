package com.heiye.clims.auth.biz.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: heiye
 * @date: 2025/10/19 下午4:33
 * @version: v1.0.0
 * @description: 注册是否完成
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterFinishRspVO {
    /**
     * 是否完成
     */
    private Boolean finish;

    /**
     * token
     */
    private String token;
}

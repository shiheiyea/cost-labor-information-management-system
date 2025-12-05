package com.heiye.clims.user.biz.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author: heiye
 * @date: 2025/10/30 下午2:27
 * @version: v1.0.0
 * @description: 查询用户主页信息
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FindUserProfileRspVO {
    private Long userId;

    private String nickname;

    private String avatar;
}

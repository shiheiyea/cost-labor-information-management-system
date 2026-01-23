package com.heiye.clims.work.biz.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: heiye
 * @date: 2025/11/04 下午2:45
 * @version: v1.0.0
 * @description: 查询今日的工作
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FindTodayWorkRspVO {
    private Long id;

    private List<String> imageUrls;

    private String workName;

    private String workContent;

    private String workPlace;

    /**
     * 工作计时：小时
     */
    private Long workHours;
    /**
     * 工作计时：分钟
     */
    private Long workMinutes;

    /**
     * 工作目标时间：小时
     */
    private Long workTargetHours;

    /**
     * 工作目标时间：分钟
     */
    private Long workTargetMinutes;

    private Integer workStatus;
}

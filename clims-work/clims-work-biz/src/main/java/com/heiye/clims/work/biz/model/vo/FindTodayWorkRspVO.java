package com.heiye.clims.work.biz.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FindTodayWorkRspVO {
    private Long id;

    private List<String> imageUrls;

    private String name;

    private String content;

    private String place;

    private Integer targetDuration;

    private Long startTime;

    private Long serverTime;

    private Integer status;
}

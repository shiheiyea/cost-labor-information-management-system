package com.heiye.clims.work.biz.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: heiye
 * @date: 2025/11/07 下午3:48
 * @version: v1.0.0
 * @description: 查询历史工作记录
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FindHistoryWorkRspVO {
    private Long id;

    private List<String> imageUrls;

    private String name;

    private String content;

    private String place;

    private Integer actualDuration;

    private Integer status;
}

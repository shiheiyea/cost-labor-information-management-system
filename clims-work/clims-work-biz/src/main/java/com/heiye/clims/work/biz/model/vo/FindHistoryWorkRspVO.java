package com.heiye.clims.work.biz.model.vo;

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
public class FindHistoryWorkRspVO {
    private Long id;

    private List<String> imageUrls;

    private String workName;

    private String workContent;

    private String workPlace;

    private String workTime;

    private Integer workStatus;
}

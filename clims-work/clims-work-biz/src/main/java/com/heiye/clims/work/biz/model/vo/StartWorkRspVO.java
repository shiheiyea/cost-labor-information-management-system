package com.heiye.clims.work.biz.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: heiye
 * @date: 2026/02/05 00:22
 * @version: v1.0.0
 * @description:
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StartWorkRspVO {
    private Long startTime;

    private Long serverTime;
}

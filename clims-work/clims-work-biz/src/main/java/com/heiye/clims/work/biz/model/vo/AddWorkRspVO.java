package com.heiye.clims.work.biz.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: heiye
 * @date: 2026/02/03 下午9:18
 * @version: v1.0.0
 * @description:
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddWorkRspVO {
    private Long workId;
}

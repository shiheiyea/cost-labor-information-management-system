package com.heiye.clims.work.biz.model.vo;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: heiye
 * @date: 2025/11/05 下午3:30
 * @version: v1.0.0
 * @description: 结束工作
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EndWorkReqVO {
    @NotNull(message = "工作 ID 不能为空")
    private Long workId;
}

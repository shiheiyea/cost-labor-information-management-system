package com.heiye.clims.work.biz.model.vo;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: heiye
 * @date: 2025/11/02 下午4:26
 * @version: v1.0.0
 * @description: 添加工作
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddWorkReqVO {
    @NotEmpty(message = "工作名称不能为空")
    private String workName;

    @NotEmpty(message = "工作内容不能为空")
    private String workContent;

    @NotEmpty(message = "工作地点不能为空")
    private String workPlace;

    @NotEmpty(message = "工作时间不能为空")
    private String workTime;

    private List<String> imageUrls;
}

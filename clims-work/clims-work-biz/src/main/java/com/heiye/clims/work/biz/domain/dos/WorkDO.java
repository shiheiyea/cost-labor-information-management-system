package com.heiye.clims.work.biz.domain.dos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author: heiye
 * @date: 2025/11/02 下午4:18
 * @version: v1.0.0
 * @description: 工作表类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_work")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WorkDO {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long userId;

    private String imageUrls;

    private String name;

    private String content;

    private String place;

    private Integer targetDuration;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Integer actualDuration;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Integer status;
}

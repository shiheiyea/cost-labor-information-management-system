package com.heiye.clims.work.biz.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class FindTodayWorkRspVO {
    private Long id;

    private List<String> imageUrls;

    private String workName;

    private String workContent;

    private String workPlace;

    private String workTime;

    private Integer workStartTime;
}

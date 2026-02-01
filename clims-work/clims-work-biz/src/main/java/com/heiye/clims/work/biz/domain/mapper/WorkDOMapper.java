package com.heiye.clims.work.biz.domain.mapper;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.heiye.clims.work.biz.domain.dos.WorkDO;
import com.heiye.clims.work.biz.enums.WorkStatusEnum;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * @author: heiye
 * @date: 2025/11/02 下午4:24
 * @version: v1.0.0
 * @description: TODO
 */
public interface WorkDOMapper extends BaseMapper<WorkDO> {
    default WorkDO findTodayWork(Long userId) {

        // 获取今日的零点和终点，避免在 SQL 中使用函数以利用索引
        LocalDateTime todayStart = LocalDate.now().atStartOfDay();
        LocalDateTime todayEnd = LocalDate.now().atTime(LocalTime.MAX);

        // 获取今日工作
        LambdaQueryWrapper<WorkDO> lambdaQueryWrapper = Wrappers.<WorkDO>lambdaQuery()
                .select(WorkDO::getId,
                        WorkDO::getName,
                        WorkDO::getPlace,
                        WorkDO::getContent,
                        WorkDO::getActualDuration,
                        WorkDO::getImageUrls,
                        WorkDO::getStatus)
                .eq(WorkDO::getUserId, userId)
                // 2. 使用范围查询
                .between(WorkDO::getCreateTime, todayStart, todayEnd)
                .orderByDesc(WorkDO::getCreateTime);

        return selectOne(lambdaQueryWrapper);
    }

    /**
     * 查询历史工作记录
     *
     * @param userId
     * @param current
     * @param size
     * @return
     */
    default Page<WorkDO> findHistoryWork(Long userId, Long current, Long size) {
        // 分页对象(查询第几页、每页多少数据)
        Page<WorkDO> page = new Page<>(current, size);

        // 构建查询条件
        LambdaQueryWrapper<WorkDO> lambdaQueryWrapper = Wrappers.<WorkDO>lambdaQuery()
                .eq(WorkDO::getUserId, userId)
                .ne(WorkDO::getStatus, WorkStatusEnum.WORKING.getCode())
                .ne(WorkDO::getStatus, WorkStatusEnum.NOT_STARTED.getCode())
                .orderByDesc(WorkDO::getCreateTime);

        return selectPage(page, lambdaQueryWrapper);
    }
}

package com.heiye.clims.work.biz.domain.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.heiye.clims.work.biz.domain.dos.WorkDO;

import java.util.List;

/**
 * @author: heiye
 * @date: 2025/11/02 下午4:24
 * @version: v1.0.0
 * @description: TODO
 */
public interface WorkDOMapper extends BaseMapper<WorkDO> {
    default WorkDO findTodayWork(Long userId) {

        // 获取今日工作
        LambdaQueryWrapper<WorkDO> lambdaQueryWrapper = Wrappers.<WorkDO>lambdaQuery()
                .select(WorkDO::getId, WorkDO::getWorkName, WorkDO::getWorkPlace, WorkDO::getWorkContent, WorkDO::getWorkTime, WorkDO::getImageUrls)
                .eq(WorkDO::getUserId, userId)
                .apply("date(create_time) = curdate()")
                .orderByDesc(WorkDO::getCreateTime);

        WorkDO workDOS = selectOne(lambdaQueryWrapper);

        return workDOS;
    }
}

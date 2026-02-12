package com.heiye.clims.work.biz.tast;

import com.github.benmanes.caffeine.cache.Cache;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.heiye.clims.work.biz.domain.dos.WorkDO;
import com.heiye.clims.work.biz.domain.mapper.WorkDOMapper;
import com.heiye.clims.work.biz.enums.WorkStatusEnum;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

/**
 * @author: heiye
 * @date: 2025/11/06 下午3:20
 * @version: v1.0.0
 * @description: 工作定时任务
 */
@Slf4j
@Component
public class WorkTask {
    @Resource
    private WorkDOMapper workDOMapper;

    /**
     * 工作超时：用户没有及时点击完成工作
     */
    @Scheduled(cron = "0 1/5 * * * ?")
    public void workTimeOut() {
        log.info("======== 定时任务：检查工作超时 ========");

        // 获取正在工作的工作, 且超过 30 分钟未更新
        List<WorkDO> workingWork = workDOMapper.findWorkingWork();

        // 批量更新工作状态
        List<WorkDO> workCompletedList = Lists.newArrayList();

        for (WorkDO workDO : workingWork) {
            // 获取当前时间
            LocalDateTime now = LocalDateTime.now();
            // 获取工作开始时间
            LocalDateTime startTime = workDO.getStartTime();

            // 当前时间减去工作开始时间，获得他们相差的分钟数
            long between = ChronoUnit.MINUTES.between(startTime, now);
            // 获取目标工作分钟数
            long targetDuration = workDO.getTargetDuration();

            // 工作已开始分钟数 >= 目标工作分钟数
            if (between >= targetDuration) {
                // 工作完成
                workDO.setEndTime(LocalDateTime.now());
                workDO.setUpdateTime(LocalDateTime.now());
                workDO.setStatus(WorkStatusEnum.COMPLETED.getCode());
                // 添加到列表
                workCompletedList.add(workDO);
            }
        }

        // 批量更新工作状态
        workDOMapper.updateById(workCompletedList);
    }
}

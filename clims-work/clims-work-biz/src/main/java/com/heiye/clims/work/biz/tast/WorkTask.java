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

    @Resource(name = "workStopwatchCaffeineCache")
    private Cache<Long, Stopwatch> workStopwatchCaffeineCache;

    @Resource
    private WorkDOMapper workDOMapper;

    /**
     * 工作超时：用户没有及时点击完成工作
     */
    @Scheduled(cron = "0 0 * * * ?")
    public void workTimeOut() {
        log.info("======== 定时任务：检查工作超时 ========");

        // 获取所有缓存中的工作计时器
        ConcurrentMap<Long, Stopwatch> workStopwatchCaffeineCacheMap = workStopwatchCaffeineCache.asMap();

        // 需要记为工作超时
        List<WorkDO> workTimeOutList = Lists.newArrayList();

        workStopwatchCaffeineCacheMap.forEach((workId, stopwatch) -> {
            // 工作超过 24 小时，需要清除该工作计时器，并记为工作超时
            if (stopwatch.elapsed().toHours() > 24) {
                // 工作超时
                WorkDO workDO = WorkDO.builder()
                        .id(workId)
                        .endTime(LocalDateTime.now())
                        .workStatus(WorkStatusEnum.TIME_OUT.getCode())
                        .updateTime(LocalDateTime.now())
                        .build();

                // 添加工作超时
                workTimeOutList.add(workDO);

                // 移除工作计时器缓存
                workStopwatchCaffeineCache.invalidate(workId);
            }
        });

        // 批量更新工作状态
        workDOMapper.updateById(workTimeOutList);
    }
}

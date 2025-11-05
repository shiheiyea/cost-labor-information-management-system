package com.heiye.clims.work.biz.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.heiye.clims.framework.common.constant.DateConstants;
import com.heiye.clims.framework.common.exception.BizException;
import com.heiye.clims.framework.common.response.Response;
import com.heiye.clims.work.biz.domain.dos.WorkDO;
import com.heiye.clims.work.biz.domain.mapper.WorkDOMapper;
import com.heiye.clims.work.biz.enums.ResponseCodeEnum;
import com.heiye.clims.work.biz.enums.WorkStatusEnum;
import com.heiye.clims.work.biz.model.vo.AddWorkReqVO;
import com.heiye.clims.work.biz.model.vo.FindTodayWorkRspVO;
import com.heiye.clims.work.biz.service.WorkService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author: heiye
 * @date: 2025/11/02 下午4:25
 * @version: v1.0.0
 * @description: 工作业务
 */
@Slf4j
@Service
public class WorkServiceImpl implements WorkService {

    @Resource
    private WorkDOMapper workDOMapper;

    // 工作计时缓存
    private final Cache<Long, Stopwatch> workStopwatchCaffeineCache = Caffeine.newBuilder()
            // 设置初始容量为 1000 个条目
            .initialCapacity(1000)
            // 设置缓存的最大容量为 10000 条
            .maximumSize(10000)
            // 24小时自动清理
            .expireAfterAccess(24, TimeUnit.HOURS)
            // 最多保存7天
            .expireAfterWrite(7, TimeUnit.DAYS)
            .build();

    /**
     * 添加工作
     *
     * @param addWorkRepVO
     * @return
     */
    @Override
    public Response<?> addWork(AddWorkReqVO addWorkRepVO) {

        // 获取登录用户ID
//        Long userId = LoginUserContextHolder.getUserId();
        Long userId = 1L;

        // 处理图片链接
        List<String> imageUrls = addWorkRepVO.getImageUrls();

        // 图片链接处理字符串
        String imageUrlsStr = null;

        if (CollUtil.isNotEmpty(imageUrls)) {

            // 创建图片链接缓冲区处理字符串
            StringBuffer imageUrlsBuffer = new StringBuffer();

            // 拼接图片链接
            imageUrls.forEach(imageUrl -> imageUrlsBuffer.append(imageUrl).append(","));

            // 截取图片链接缓冲区处理字符串
            imageUrlsStr = imageUrlsBuffer.toString();
        }

        // 创建工作 DO
        WorkDO workDO = WorkDO.builder()
                .userId(userId)
                .imageUrls(imageUrlsStr)
                .workName(addWorkRepVO.getWorkName())
                .workPlace(addWorkRepVO.getWorkPlace())
                .workContent(addWorkRepVO.getWorkContent())
                .workTime(addWorkRepVO.getWorkTime())
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .workStatus(WorkStatusEnum.NOT_STARTED.getCode())
                .build();

        workDOMapper.insert(workDO);

        return Response.success();
    }

    /**
     * 查询今日的工作
     *
     * @return
     */
    @Override
    public Response<?> findTodayWork() {
        // TODO 获取登录用户ID
        Long userId = 1L;

        // 获取今日工作 只查询先添加的工作
        WorkDO workDO = workDOMapper.findTodayWork(userId);

        // 构建返回结果
        FindTodayWorkRspVO findTodayWorkRspVO = null;

        // 查询到今日工作就构建返回结果
        if (Objects.nonNull(workDO)) {

            // 获取图片链接处理字符串
            String imageUrlsStr = workDO.getImageUrls();

            // 处理图片链接
            List<String> imageUrls = null;
            // 不为空则处理链接
            if (StringUtils.isNotBlank(imageUrlsStr)) {
                imageUrls = Lists.newArrayList(imageUrlsStr.split(","));
            }

            findTodayWorkRspVO = FindTodayWorkRspVO.builder()
                    .id(workDO.getId())
                    .workName(workDO.getWorkName())
                    .workPlace(workDO.getWorkPlace())
                    .workContent(workDO.getWorkContent())
                    .workTime(workDO.getWorkTime())
                    .imageUrls(imageUrls)
                    .build();
        }

        return Response.success(findTodayWorkRspVO);
    }

    /**
     * 开始工作计时
     *
     * @param id
     * @return
     */
    @Override
    public Response<?> startWork(Long id) {
        // 查询该工作是否存在
        WorkDO workDO = workDOMapper.selectById(id);

        // 为空抛出异常
        if (Objects.isNull(workDO)) {
            throw new BizException(ResponseCodeEnum.WORK_NOT_EXIST);
        }

        // 创建工作计时器
        Stopwatch stopwatch = Stopwatch.createStarted();
        // 缓存工作计时器
        workStopwatchCaffeineCache.put(id, stopwatch);

        // 更新工作开始时间和工作状态
        workDO.setWorkStartTime(LocalDateTime.now());
        workDO.setWorkStatus(WorkStatusEnum.WORKING.getCode());
        workDO.setUpdateTime(LocalDateTime.now());

        workDOMapper.updateById(workDO);
        return Response.success();
    }

    /**
     * 结束工作计时
     *
     * @param id
     * @return
     */
    @Override
    public Response<?> endWork(Long id) {
        // 查询该工作是否存在
        WorkDO workDO = workDOMapper.selectById(id);

        // 为空抛出异常
        if (Objects.isNull(workDO)) {
            throw new BizException(ResponseCodeEnum.WORK_NOT_EXIST);
        }

        // 从缓存中获取 Stopwatch
        Stopwatch stopwatch = workStopwatchCaffeineCache.getIfPresent(id);

        // 缓存中不存在工作计时器
        if (Objects.isNull(stopwatch)) {
            throw new BizException(ResponseCodeEnum.WORK_STATUS_ERROR);
        }

        // 获取工作计时器已使用的毫秒数
        long elapsedMillis = stopwatch.elapsed(TimeUnit.MILLISECONDS);
        // 获取工作计时器已使用的时间
        Duration duration = Duration.ofMillis(elapsedMillis);
        // 获取到已使用的时间小时和分钟
        long hours = duration.toHours();
        long minutes = duration.toMinutes() - hours * 60;

        // 查询制定的目标工作时间
        String workTime = workDO.getWorkTime();
        String[] split = workTime.split(":");
        // 获取目标时间小时数和分钟
        long targetHours = Long.parseLong(split[0]);
        long targetMinutes = Long.parseLong(split[1]);

        // 当工作小时已超过目标小时
        // 或者工作小时已等于目标小时并且已工作的分钟数大于等于目标时间分钟数
        if (hours > targetHours ||
                (hours == targetHours && minutes >= targetMinutes)) {
            // 正常完成工作
            workDO.setWorkStatus(WorkStatusEnum.COMPLETED.getCode());
        } else {
            // 提前完成工作
            workDO.setWorkStatus(WorkStatusEnum.EARLY_COMPLETED.getCode());
        }

        // 更新工作状态和时间
        workDO.setUpdateTime(LocalDateTime.now());
        workDOMapper.updateById(workDO);

        // 停止工作计时器
        stopwatch.stop();
        // 删除缓存中的工作计时器
        workStopwatchCaffeineCache.invalidate(id);

        return Response.success();
    }
}

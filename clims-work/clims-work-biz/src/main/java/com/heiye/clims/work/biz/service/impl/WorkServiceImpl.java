package com.heiye.clims.work.biz.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.benmanes.caffeine.cache.Cache;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.heiye.clims.framework.common.exception.BizException;
import com.heiye.clims.framework.common.response.Response;
import com.heiye.clims.framework.mybatis.response.PageResponse;
import com.heiye.clims.work.biz.domain.dos.WorkDO;
import com.heiye.clims.work.biz.domain.mapper.WorkDOMapper;
import com.heiye.clims.work.biz.enums.ResponseCodeEnum;
import com.heiye.clims.work.biz.enums.WorkStatusEnum;
import com.heiye.clims.work.biz.model.vo.*;
import com.heiye.clims.work.biz.service.WorkService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
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

    @Resource(name = "workStopwatchCaffeineCache")
    private Cache<Long, Stopwatch> workStopwatchCaffeineCache;

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
                .workTargetTime(addWorkRepVO.getWorkTargetTime())
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

            // 处理图片链接
            List<String> imageUrls = imageUrlStrtoList(workDO.getImageUrls());

            // 处理工作计时
            Long workHours = null;
            Long workMinutes = null;
            Stopwatch stopwatch = workStopwatchCaffeineCache.getIfPresent(workDO.getId());
            if (Objects.nonNull(stopwatch)) {
                // 获取工作时长
                Duration duration = stopwatch.elapsed();

                // 赋值
                workHours = duration.toHours();
                workMinutes = duration.toMinutes() - duration.toHours() * 60;
            }

            // 处理工作目标时间
            Long workTargetHours = null;
            Long workTargetMinutes = null;
            // 获取工作目标时间
            String workTargetTime = workDO.getWorkTargetTime();
            if (StringUtils.isNotBlank(workTargetTime)) {
                // 切分字符串
                String[] split = workTargetTime.split(":");

                // 赋值
                workTargetHours = Long.valueOf(split[0]);
                workTargetMinutes = Long.parseLong(split[1]);
            }

            findTodayWorkRspVO = FindTodayWorkRspVO.builder()
                    .id(workDO.getId())
                    .workName(workDO.getWorkName())
                    .workPlace(workDO.getWorkPlace())
                    .workContent(workDO.getWorkContent())
                    .imageUrls(imageUrls)
                    .workHours(workHours)
                    .workMinutes(workMinutes)
                    .workTargetHours(workTargetHours)
                    .workTargetMinutes(workTargetMinutes)
                    .workStatus(workDO.getWorkStatus())
                    .build();
        }

        return Response.success(findTodayWorkRspVO);
    }

    /**
     * 开始工作计时
     *
     * @param startWorkReqVO
     * @return
     */
    @Override
    public Response<?> startWork(StartWorkReqVO startWorkReqVO) {
        // 获取工作 ID
        Long id = startWorkReqVO.getWorkId();

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
     * @param endWorkReqVO
     * @return
     */
    @Override
    public Response<?> endWork(EndWorkReqVO endWorkReqVO) {
        // 获取工作 ID
        Long id = endWorkReqVO.getWorkId();

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
        String workTargetTime = workDO.getWorkTargetTime();
        String[] split = workTargetTime.split(":");
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

    /**
     * 查询历史工作记录
     *
     * @param findHistoryWorkReqVO
     * @return
     */
    @Override
    public Response<?> findHistoryWork(FindHistoryWorkReqVO findHistoryWorkReqVO) {
        // TODO: 获取登录用户 ID
        Long userId = 1L;

        // 获取页码和页数
        Long current = findHistoryWorkReqVO.getCurrent();
        Long size = findHistoryWorkReqVO.getSize();

        // 分页查询数据
        Page<WorkDO> workDOPage = workDOMapper.findHistoryWork(userId, current, size);

        // 获取分页数据
        List<WorkDO> records = workDOPage.getRecords();

        // 构建返回数据
        List<FindHistoryWorkRspVO> findHistoryWorkRspVOList = Lists.newArrayList();

        records.forEach(workDO -> {
            // 处理图片链接
            List<String> imageUrls = imageUrlStrtoList(workDO.getImageUrls());

            // 工作时间
            String workTargetTime = workDO.getWorkTargetTime();

            // 获取工作状态
            Integer workStatus = workDO.getWorkStatus();

            // 工作完成时间
            Long workHours = null;
            Long workMinutes = null;

            // 获取工作状态枚举
            WorkStatusEnum workStatusEnum = WorkStatusEnum.getWorkStatusEnum(workStatus);

            switch (workStatusEnum) {
                case COMPLETED, TIME_OUT -> {
                    // 获取工作完成时间
                    String[] split = workTargetTime.split(":");
                    // 赋值工作完成时间小时数和分钟
                    workHours = Long.valueOf(split[0]);
                    workMinutes = Long.valueOf(split[1]);
                }
                case EARLY_COMPLETED -> {
                    // 计算提前完成工作时间
                    workHours = workDO.getWorkStartTime().until(workDO.getUpdateTime(), ChronoUnit.HOURS);
                    workMinutes = workDO.getWorkStartTime().until(workDO.getUpdateTime(), ChronoUnit.MINUTES) - workHours * 60;
                }
            }

            // 构建返回数据
            FindHistoryWorkRspVO findHistoryWorkRspVO = FindHistoryWorkRspVO.builder()
                    .id(workDO.getId())
                    .imageUrls(imageUrls)
                    .workName(workDO.getWorkName())
                    .workPlace(workDO.getWorkPlace())
                    .workStatus(workDO.getWorkStatus())
                    .workContent(workDO.getWorkContent())
                    .workTimeHours(workHours)
                    .workTimeMinutes(workMinutes)
                    .build();

            findHistoryWorkRspVOList.add(findHistoryWorkRspVO);
        });

        return PageResponse.success(workDOPage, findHistoryWorkRspVOList);
    }

    /**
     * 图片链接处理
     *
     * @param imageUrlsStr
     * @return
     */
    private static List<String> imageUrlStrtoList(String imageUrlsStr) {

        // 处理图片链接
        List<String> imageUrls = null;
        // 不为空则处理链接
        if (StringUtils.isNotBlank(imageUrlsStr)) {
            imageUrls = Lists.newArrayList(imageUrlsStr.split(","));
        }

        return imageUrls;
    }
}

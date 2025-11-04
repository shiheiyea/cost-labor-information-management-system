package com.heiye.clims.work.biz.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.heiye.clims.framework.common.constant.DateConstants;
import com.heiye.clims.framework.common.response.Response;
import com.heiye.clims.work.biz.domain.dos.WorkDO;
import com.heiye.clims.work.biz.domain.mapper.WorkDOMapper;
import com.heiye.clims.work.biz.enums.WorkStatusEnum;
import com.heiye.clims.work.biz.model.vo.AddWorkReqVO;
import com.heiye.clims.work.biz.model.vo.FindTodayWorkRspVO;
import com.heiye.clims.work.biz.service.WorkService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

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

            findTodayWorkRspVO = FindTodayWorkRspVO.builder()
                    .id(workDO.getId())
                    .workName(workDO.getWorkName())
                    .workPlace(workDO.getWorkPlace())
                    .workContent(workDO.getWorkContent())
                    .workTime(workDO.getWorkTime())
                    .imageUrls(workDO.getImageUrls())
                    .build();
        }

        return Response.success(findTodayWorkRspVO);
    }
}

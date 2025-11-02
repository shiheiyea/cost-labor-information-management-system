package com.heiye.clims.work.biz.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.heiye.clims.framework.common.response.Response;
import com.heiye.clims.framework.common.thread.LoginUserContextHolder;
import com.heiye.clims.work.biz.domain.dos.WorkDO;
import com.heiye.clims.work.biz.domain.mapper.WorkDOMapper;
import com.heiye.clims.work.biz.enums.WorkStatusEnum;
import com.heiye.clims.work.biz.model.vo.AddWorkVO;
import com.heiye.clims.work.biz.service.WorkService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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
     * @param addWorkVO
     * @return
     */
    @Override
    public Response<?> addWork(AddWorkVO addWorkVO) {

        // 获取登录用户ID
//        Long userId = LoginUserContextHolder.getUserId();
        Long userId = 1L;

        // 处理图片链接
        List<String> imageUrls = addWorkVO.getImageUrls();

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
                .workName(addWorkVO.getWorkName())
                .workPlace(addWorkVO.getWorkPlace())
                .workContent(addWorkVO.getWorkContent())
                .workTime(addWorkVO.getWorkTime())
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .workStatus(WorkStatusEnum.NOT_STARTED.getCode())
                .build();

        workDOMapper.insert(workDO);

        return Response.success();
    }
}

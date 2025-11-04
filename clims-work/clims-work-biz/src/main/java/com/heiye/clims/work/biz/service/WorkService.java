package com.heiye.clims.work.biz.service;

import com.heiye.clims.framework.common.response.Response;
import com.heiye.clims.work.biz.model.vo.AddWorkReqVO;

/**
 * @author: heiye
 * @date: 2025/11/02 下午4:25
 * @version: v1.0.0
 * @description: 工作业务
 */
public interface WorkService {
    /**
     * 添加工作
     *
     * @param addWorkRepVO
     * @return
     */
    Response<?> addWork(AddWorkReqVO addWorkRepVO);


    /**
     * 查询今日的工作
     *
     * @return
     */
    Response<?> findTodayWork();
}

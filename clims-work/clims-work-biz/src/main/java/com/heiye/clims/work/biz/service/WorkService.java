package com.heiye.clims.work.biz.service;

import com.heiye.clims.framework.mybatis.response.PageResponse;
import com.heiye.clims.framework.common.response.Response;
import com.heiye.clims.work.biz.model.vo.AddWorkReqVO;
import com.heiye.clims.work.biz.model.vo.EndWorkReqVO;
import com.heiye.clims.work.biz.model.vo.FindHistoryWorkReqVO;
import com.heiye.clims.work.biz.model.vo.StartWorkReqVO;

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

    /**
     * 开始工作计时
     *
     * @return
     */
    Response<?> startWork(StartWorkReqVO startWorkReqVO);

    /**
     * 结束工作计时
     *
     * @param endWorkReqVO
     * @return
     */
    Response<?> endWork(EndWorkReqVO endWorkReqVO);

    /**
     * 查询历史工作记录
     *
     * @param findHistoryWorkReqVO
     * @return
     */
    PageResponse<?> findHistoryWork(FindHistoryWorkReqVO findHistoryWorkReqVO);
}

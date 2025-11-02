package com.heiye.clims.work.biz.service;

import com.heiye.clims.framework.common.response.Response;
import com.heiye.clims.work.biz.domain.dos.WorkDO;
import com.heiye.clims.work.biz.model.vo.AddWorkVO;

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
     * @param addWorkVO
     * @return
     */
    Response<?> addWork(AddWorkVO addWorkVO);
}

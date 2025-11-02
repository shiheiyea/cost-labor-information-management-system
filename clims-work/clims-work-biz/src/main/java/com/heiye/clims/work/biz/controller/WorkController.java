package com.heiye.clims.work.biz.controller;

import com.heiye.clims.framework.common.response.Response;
import com.heiye.clims.work.biz.model.vo.AddWorkVO;
import com.heiye.clims.work.biz.service.WorkService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: heiye
 * @date: 2025/11/02 下午4:43
 * @version: v1.0.0
 * @description: 工作业务
 */
@Service
@Validated
@RestController
@RequestMapping("/work")
public class WorkController {

    @Resource
    private WorkService workService;

    /**
     * 添加工作
     *
     * @param addWorkVO
     * @return
     */
    @PostMapping("/add")
    Response<?> addWork(@RequestBody @Valid AddWorkVO addWorkVO) {
        return workService.addWork(addWorkVO);
    }
}

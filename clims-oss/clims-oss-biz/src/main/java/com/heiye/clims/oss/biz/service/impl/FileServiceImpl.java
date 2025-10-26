package com.heiye.clims.oss.biz.service.impl;

import com.heiye.clims.framework.common.response.Response;
import com.heiye.clims.oss.biz.factory.FileStrategyFactory;
import com.heiye.clims.oss.biz.service.FileService;
import com.heiye.clims.oss.biz.strategy.FileStrategy;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: heiye
 * @date: 2025/10/26 下午4:32
 * @version: v1.0.0
 * @description: 文件上传服务
 */
@Service
public class FileServiceImpl implements FileService {

    @Resource
    private FileStrategyFactory fileStrategyFactory;

    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    @Override
    public Response<?> uploadFile(MultipartFile file) {
        // 获取文件上传策略
        FileStrategy fileStrategy = fileStrategyFactory.getStrategy();
        // 上传文件
        String url = fileStrategy.uploadFile(file);

        return Response.success(url);
    }
}

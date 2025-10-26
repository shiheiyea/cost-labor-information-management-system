package com.heiye.clims.oss.biz.service;

import com.heiye.clims.framework.common.response.Response;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: heiye
 * @date: 2025/10/26 下午4:32
 * @version: v1.0.0
 * @description: 文件上传服务
 */
public interface FileService {
    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    Response<?> uploadFile(MultipartFile file);
}

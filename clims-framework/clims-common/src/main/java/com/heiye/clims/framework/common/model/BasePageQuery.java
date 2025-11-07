package com.heiye.clims.framework.common.model;

import lombok.Data;

/**
 * @author: heiye
 * @date: 2025/10/10 下午8:35
 * @version: v1.0.0
 * @description: TODO
 */
@Data
public class BasePageQuery {
    /**
     * 当前页码, 默认第一页
     */
    private Long current = 1L;
    /**
     * 每页展示的数据数量，默认每页展示 10 条数据
     */
    private Long size = 10L;
}

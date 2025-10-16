package com.heiye.clims.auth.biz.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author: heiye
 * @date: 2025/10/16 下午2:27
 * @version: v1.0.0
 * @description: MybatisPlus 配置类
 */
@Configuration
@MapperScan("com.heiye.clims.auth.biz.domain.mapper")
public class MybatisPlusConfig {
}

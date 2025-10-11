package com.heiye.clims.auth.biz.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @author: heiye
 * @date: 2025/10/06 下午6:44
 * @version: v1.0.0
 * @description: CaffeineCache配置类
 */
@Configuration
public class CacheConfig {
    /**
     * 验证码本地缓存
     */
    @Bean(name = "emailCaffeineCache")
    public Cache<String, Object> emailCaffeineCache() {
        return Caffeine.newBuilder()
                // 设置初始容量为 10000 个条目
                .initialCapacity(10000)
                // 设置缓存的最大容量为 10000 条
                .maximumSize(10000)
                // 设置缓存条目在写入后 10 分钟过期
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .build();
    }
}

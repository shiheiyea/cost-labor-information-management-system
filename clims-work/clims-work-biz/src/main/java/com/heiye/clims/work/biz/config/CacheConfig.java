package com.heiye.clims.work.biz.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.common.base.Stopwatch;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @author: heiye
 * @date: 2025/11/05 下午2:53
 * @version: v1.0.0
 * @description: CaffeineCache配置类
 */
@Configuration
public class CacheConfig {
    /**
     * 验证码本地缓存
     */
    @Bean(name = "workStopwatchCaffeineCache")
    public Cache<Long, Stopwatch> workStopwatchCaffeineCache() {
        return Caffeine.newBuilder()
                // 设置初始容量为 1000 个条目
                .initialCapacity(1000)
                // 设置缓存的最大容量为 10000 条
                .maximumSize(10000)
                // 24小时自动清理
                .expireAfterAccess(24, TimeUnit.HOURS)
                // 最多保存7天
                .expireAfterWrite(7, TimeUnit.DAYS)
                .build();
    }
}

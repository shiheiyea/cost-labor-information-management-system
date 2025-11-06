package com.heiye.clims.work.biz.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalCause;
import com.google.common.base.Stopwatch;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author: heiye
 * @date: 2025/11/06 下午3:23
 * @version: v1.0.0
 * @description: TODO
 */
@Configuration(value = "workCacheConfig")
public class CacheConfig {

    /**
     * 工作计时器缓存
     * @return
     */
    @Bean(name = "workStopwatchCaffeineCache")
    public Cache<Long, Stopwatch> workStopwatchCaffeineCache() {
        return Caffeine.newBuilder()
                // 设置初始容量为 1000 个条目
                .initialCapacity(1000)
                // 设置缓存的最大容量为 10000 条
                .maximumSize(10000)
                // 最多保存24小时
                .expireAfterWrite(7, TimeUnit.DAYS)
                .build();
    }
}

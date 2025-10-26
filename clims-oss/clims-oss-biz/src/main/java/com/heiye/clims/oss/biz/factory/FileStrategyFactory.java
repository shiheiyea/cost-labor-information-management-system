package com.heiye.clims.oss.biz.factory;

import com.heiye.clims.oss.biz.enums.StorageTypeEnum;
import com.heiye.clims.oss.biz.strategy.FileStrategy;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: heiye
 * @date: 2025/10/22 下午4:19
 * @version: v1.0.0
 * @description: 文件策略工厂
 */
@Configuration
public class FileStrategyFactory {

    @Value("${storage.type}")
    private String strategyType;

    /**
     * 该处存放所有文件上传策略 --- 自动装配
     */
    @Resource
    private List<FileStrategy> fileStrategyList;

    private final Map<StorageTypeEnum, FileStrategy> fileStrategyMap = new HashMap<>();

    /**
     * @PostConstruct： 构造方法执行完成之后执行将 loginStrategyMap 填充
     */
    @PostConstruct
    public void init() {
        fileStrategyList.forEach(fileStrategy ->
                fileStrategyMap.put(fileStrategy.getStorageType(), fileStrategy));
    }

    public FileStrategy getStrategy() {
        StorageTypeEnum storageTypeEnum = StorageTypeEnum.getStorageType(strategyType);
        return fileStrategyMap.get(storageTypeEnum);
    }
}

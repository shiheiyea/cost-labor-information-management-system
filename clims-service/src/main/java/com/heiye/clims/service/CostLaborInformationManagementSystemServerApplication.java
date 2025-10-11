package com.heiye.clims.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: heiye
 * @date: 2025/09/17 下午8:09
 * @version: v1.0.0
 * @description: 启动类
 */
@SpringBootApplication(scanBasePackages = "com.heiye.clims")
public class CostLaborInformationManagementSystemServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(CostLaborInformationManagementSystemServerApplication.class, args);
    }
}

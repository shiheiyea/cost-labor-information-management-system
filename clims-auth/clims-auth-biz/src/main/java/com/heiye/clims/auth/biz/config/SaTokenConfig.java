package com.heiye.clims.auth.biz.config;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.filter.SaServletFilter;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: heiye
 * @date: 2025/10/21 下午9:27
 * @version: v1.0.0
 * @description: [Sa-Token 权限认证] 配置类
 */
@Slf4j
@Configuration
public class SaTokenConfig {
    /**
     * 注册 [Sa-Token全局过滤器]
     */
    @Bean
    public SaServletFilter getSaServletFilter() {
        return new SaServletFilter()
                // 指定 拦截路由 与 放行路由
                .addInclude("/**")
                // 认证函数: 每次请求执行
                .setAuth(obj -> {
                    log.info("==================> SaReactorFilter, Path: {}", SaHolder.getRequest().getRequestPath());

                    // 登录认证 -- 拦截所有路由
                    SaRouter.match("/**")
                            // 排除 /login 用于开放登录
                            .notMatch("/login")
                            // 排除 /register 用于注册
                            .notMatch("/register")
                            // 排除 /verify/code/send/email 用于发送验证码
                            .notMatch("/verify/code/send/email")
                            // 认证函数: 每次请求执行
                            .check(StpUtil::checkLogin);
                });
    }
}

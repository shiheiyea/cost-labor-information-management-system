package com.heiye.clims.auth.biz.config;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.filter.SaServletFilter;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import com.heiye.clims.auth.biz.enums.AuthErrorTypeEnum;
import com.heiye.clims.auth.biz.enums.ResponseCodeEnum;
import com.heiye.clims.framework.common.exception.BizException;
import com.heiye.clims.framework.common.response.Response;
import com.heiye.clims.framework.common.util.JsonUtils;
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
                            // 排除 /logout 用于登出
                            .notMatch("/logout")
                            // 认证函数: 每次请求执行
                            .check(StpUtil::checkLogin);
                })
                .setError(e -> {
                    // 构建认证服务通用错误
                    BizException bizException = new BizException(ResponseCodeEnum.AUTH_ERROR);

                    // 转换为 NotLoginException 类型，判断登录失败情况
                    if (e instanceof NotLoginException nle) {
                        log.info("==================> NotLoginException, Code: {}, Type: {}, Message: {}", nle.getCode(), nle.getType(), nle.getMessage());

                        // 处理 NotLoginException 转化为 BizException
                        bizException = notLoginExceptionProcess(nle);
                    }
                    log.warn("auth error, errorCode: {}, errorMessage: {}", bizException.getErrorCode(), bizException.getErrorMessage());


                    // 构建返回 json 字符串结果
                    return JsonUtils.toJsonString(Response.fail(bizException.getErrorCode(), bizException.getErrorMessage()));
                });
    }

    private BizException notLoginExceptionProcess(NotLoginException e) {
        // 获取异常类型
        String type = e.getType();

        // 获取异常枚举
        AuthErrorTypeEnum authErrorTypeEnum = AuthErrorTypeEnum.getAuthErrorTypeEnum(type);

        // 匹配异常类型
        return switch (authErrorTypeEnum) {
            case NOT_TOKEN ->  new BizException(ResponseCodeEnum.NOT_TOKEN);
            case INVALID_TOKEN -> new BizException(ResponseCodeEnum.INVALID_TOKEN);
            case TOKEN_TIMEOUT -> new BizException(ResponseCodeEnum.TOKEN_TIMEOUT);
            case BE_REPLACED ->  new BizException(ResponseCodeEnum.BE_REPLACED);
            case KICK_OUT ->  new BizException(ResponseCodeEnum.KICK_OUT);
            case TOKEN_FREEZE ->  new BizException(ResponseCodeEnum.TOKEN_FREEZE);
            case NO_PREFIX ->  new BizException(ResponseCodeEnum.NO_PREFIX);
        };
    }
}

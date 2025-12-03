package com.heiye.clims.framework.web.filter;

import cn.dev33.satoken.stp.StpUtil;
import com.heiye.clims.framework.common.constant.GlobalConstants;
import com.heiye.clims.framework.common.thread.LoginUserContextHolder;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

/**
 * @author: heiye
 * @date: 2024/09/24 下午2:04
 * @version: v1.0.0
 * @description: 转发请求时，将 Header 请求头中的 Token 转换为 userId ，并设置到 ThreadLocal 中
 */
@Slf4j
@Component
@Order(-200)
public class UserIdContextFiler extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 获取请求头中，Token 值
        String tokenValue = request.getHeader(GlobalConstants.TOKEN_HEADER_KEY);

        if (StringUtils.isBlank(tokenValue)) {
            // 若请求头中未携带 Token，则直接放行
            filterChain.doFilter(request, response);
            return;
        }

        // 将 Token 前缀去除
        String token = tokenValue.replace(GlobalConstants.TOKEN_HEADER_VALUE_PREFIX, "");

        // 获取指定 token 对应的账号id，如果未登录，则返回 null
        Object userId = StpUtil.getLoginIdByToken(token);

        if (Objects.isNull(userId)) {
            // 若没有登录，则直接放行
            filterChain.doFilter(request, response);
            return;
        }

        // 如果 header 中存在 userId，则设置到 ThreadLocal 中
        log.info("===== 设置 userId 到 ThreadLocal 中， 用户id : {}", userId);
        LoginUserContextHolder.setUserId(userId);

        try {
            // 将请求和响应传递给过滤链中的下一个过滤器。
            filterChain.doFilter(request, response);
        } finally {
            // 一定要删除 ThreadLocal ，防止内存泄露
            LoginUserContextHolder.clear();
            log.info("===== 删除 ThreadLocal， userId: {}", userId);
        }
    }
}

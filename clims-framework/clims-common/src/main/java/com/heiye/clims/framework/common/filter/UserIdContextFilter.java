package com.heiye.clims.framework.common.filter;

import com.heiye.clims.framework.common.constant.GlobalConstants;
import com.heiye.clims.framework.common.thread.LoginUserContextHolder;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @author: heiye
 * @date: 2024/09/24 下午2:49
 * @version: v1.0.0
 * @description: 提取请求头中的用户 id 保存到上下文中，以方便后续使用
 */
@Slf4j
@Component
public class UserIdContextFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        // 从请求头中获取用户id
        String userId = request.getHeader(GlobalConstants.USER_ID);

        log.info("## AuthContextFilter, 用户id: {}", userId);

        // 判断请求头是否存在用户id
        if (StringUtils.isBlank(userId)) {
            // 若为空，则直接放行
            chain.doFilter(request, response);
            return;
        }

        // 如果 header 中存在 userId，则设置到 ThreadLocal 中
        log.info("===== 设置 userId 到 ThreadLocal 中， 用户id : {}", userId);
        LoginUserContextHolder.setUserId(userId);

        try {
            // 将请求和响应传递给过滤链中的下一个过滤器。
            chain.doFilter(request, response);
        } finally {
            // 一定要删除 ThreadLocal ，防止内存泄露
            LoginUserContextHolder.clear();
            log.info("===== 删除 ThreadLocal， userId: {}", userId);
        }
    }
}

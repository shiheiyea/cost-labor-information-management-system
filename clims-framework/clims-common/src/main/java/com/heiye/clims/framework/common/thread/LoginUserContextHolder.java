package com.heiye.clims.framework.common.thread;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.heiye.clims.framework.common.constant.GlobalConstants;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author: heiye
 * @date: 2024/09/20 下午2:23
 * @version: v1.0.0
 * @description: 登录用户线程工具类
 */
public class LoginUserContextHolder {

    // 初始化一个 ThreadLocal 变量
    private final static ThreadLocal<Map<String, Object>> USER_THREAD_LOCAL = TransmittableThreadLocal.withInitial(HashMap::new);

    //存入线程
    public static void setUserId(Object value) {
        USER_THREAD_LOCAL.get().put(GlobalConstants.USER_ID, value);
    }

    //从线程中获取
    public static Long getUserId() {
        Object value = USER_THREAD_LOCAL.get().get(GlobalConstants.USER_ID);
        if (Objects.isNull(value)) {
            return null;
        }
        return Long.valueOf(value.toString());
    }

    //清理
    public static void clear() {
        USER_THREAD_LOCAL.remove();
    }
}

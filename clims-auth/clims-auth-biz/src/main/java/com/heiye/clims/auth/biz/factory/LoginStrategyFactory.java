package com.heiye.clims.auth.biz.factory;

import com.heiye.clims.auth.biz.enums.LoginTypeEnum;
import com.heiye.clims.auth.biz.strategy.LoginStrategy;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: heiye
 * @date: 2025/10/19 下午3:18
 * @version: v1.0.0
 * @description: 登录策略工厂类
 */
@Component
public class LoginStrategyFactory {

    /**
     * 该处存放所有登录策略 --- 自动装配
     */
    @Resource
    private List<LoginStrategy> loginStrategyList;

    private final Map<LoginTypeEnum, LoginStrategy> loginStrategyMap = new HashMap<>();

    public LoginStrategy getStrategy(Integer type) {
        LoginTypeEnum loginTypeEnum = LoginTypeEnum.valueOf(type);
        return loginStrategyMap.get(loginTypeEnum);
    }

    /**
     * @PostConstruct： 构造方法执行完成之后执行将 loginStrategyMap 填充
     */
    @PostConstruct
    public void init() {
        loginStrategyList.forEach(loginStrategy ->
                loginStrategyMap.put(loginStrategy.getLoginType(), loginStrategy));
    }
}

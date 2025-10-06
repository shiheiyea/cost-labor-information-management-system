package com.heiyea.auth.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.example.costLaborInformationManagementSystem.common.exception.BizException;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.heiyea.auth.enums.ResponseCodeEnum;
import com.heiyea.auth.helper.EmailHelper;
import com.heiyea.auth.model.vo.SendEmailVerificationCodeReqVO;
import com.heiyea.auth.service.VerifyCodeService;
import com.example.costLaborInformationManagementSystem.common.response.Response;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author: heiye
 * @date: 2025/10/06 下午5:43
 * @version: v1.0.0
 * @description: 验证码接口
 */
@Slf4j
@Service
public class VerifyCodeServiceImpl implements VerifyCodeService {

    @Resource
    private EmailHelper emailHelper;

    @Resource(name = "taskExecutor")
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    /**
     * 验证码本地缓存
     */
    private static final Cache<String, String> LOCAL_CACHE = Caffeine.newBuilder()
            // 设置初始容量为 10000 个条目
            .initialCapacity(10000)
            // 设置缓存的最大容量为 10000 条
            .maximumSize(10000)
            // 设置缓存条目在写入后 10 分钟过期
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .build();

    /**
     * 发送邮箱验证码
     *
     * @param sendEmailVerificationCodeReqVO
     * @return
     */
    @Override
    public Response<?> sendEmailVerificationCode(SendEmailVerificationCodeReqVO sendEmailVerificationCodeReqVO) {
        // 1. 发送邮箱验证码
        // 获取邮箱
        String email = sendEmailVerificationCodeReqVO.getEmail();

        // 判断该邮箱是否已申请过验证码
        String localCode = LOCAL_CACHE.getIfPresent(email);
        // 已申请过验证码提醒申请过于频繁
        if (StringUtils.isNotBlank(localCode)) {
            throw new BizException(ResponseCodeEnum.VERIFICATION_CODE_SEND_FREQUENTLY);
        }

        // 生成验证码，仅数字
        String code = RandomUtil.randomNumbers(6);

        // 发送验证码
        threadPoolTaskExecutor.execute(() ->
                emailHelper.sendVerifyCode(email, code, 10));

        // 2. 将验证码存入 Caffeine 中
        LOCAL_CACHE.put(email, code);

        return Response.success();
    }
}

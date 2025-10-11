package com.heiye.clims.auth.biz.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.heiye.clims.common.exception.BizException;
import com.github.benmanes.caffeine.cache.Cache;
import com.heiye.clims.common.response.Response;
import com.heiye.clims.auth.biz.enums.ResponseCodeEnum;
import com.heiye.clims.auth.biz.helper.EmailHelper;
import com.heiye.clims.auth.biz.model.vo.SendEmailVerificationCodeReqVO;
import com.heiye.clims.auth.biz.service.VerifyCodeService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

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

    @Resource(name = "emailCaffeineCache")
    private Cache<String, String> emailCaffeineCache;

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
        String localCode = emailCaffeineCache.getIfPresent(email);
        // 已申请过验证码提醒申请过于频繁
        if (StringUtils.isNotBlank(localCode)) {
            throw new BizException(ResponseCodeEnum.VERIFICATION_CODE_SEND_FREQUENTLY);
        }

        // 生成验证码，仅 6 位数字
        String code = RandomUtil.randomNumbers(6);

        // 发送验证码
        threadPoolTaskExecutor.execute(() ->
                emailHelper.sendVerifyCode(email, code, 10));

        // 2. 将验证码存入 Caffeine 中
        emailCaffeineCache.put(email, code);

        return Response.success();
    }
}

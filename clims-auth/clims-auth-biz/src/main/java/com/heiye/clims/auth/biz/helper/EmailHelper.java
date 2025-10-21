package com.heiye.clims.auth.biz.helper;

import com.heiye.clims.common.exception.BizException;
import com.heiye.clims.common.enums.ResponseCodeEnum;
import jakarta.annotation.Resource;
import jakarta.mail.internet.InternetAddress;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author: heiye
 * @date: 2025/02/21 下午10:12
 * @version: v1.0.0
 * @description: 发送邮件业务
 */
@Slf4j
@Component
public class EmailHelper {

    @Value("${spring.mail.username}")
    private String username;

    @Resource
    private JavaMailSender javaMailSender;

    @SneakyThrows
    public void sendVerifyCode(String email, String code, Integer timeout) {
        // 构建Helper
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(javaMailSender.createMimeMessage(), true);
        // 邮件发信人
        mimeMessageHelper.setFrom(new InternetAddress("人工造价管理系统<" + username + ">"));
        // 邮件收信人
        mimeMessageHelper.setTo(email);
        // 邮件主题
        mimeMessageHelper.setSubject("这是您的验证码请妥善保存");
        // 邮件发送时间
        mimeMessageHelper.setSentDate(new Date());
        // 邮件内容
        String format = String.format("您的验证码为：%s, 请在%s分钟内使用", code, timeout);
        // html 发送邮件
        mimeMessageHelper.setText(format);

        try {
            log.info("==> 开始邮件发送, email: {}, code: {}", email, code);

            // 发送请求
            javaMailSender.send(mimeMessageHelper.getMimeMessage());

            log.info("==> 邮件发送成功");
        } catch (Exception e) {
            log.error("==> 邮件发送错误: ", e);
            throw new BizException(ResponseCodeEnum.EMAIL_VERIFICATION_CODE_SEND_ERROR);
        }
    }
}

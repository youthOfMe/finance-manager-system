package com.yangge.finance.biz.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import com.yangge.finance.biz.constant.RedisKeyContant;
import com.yangge.finance.biz.dto.form.GetBase64CodeRequest;
import com.yangge.finance.biz.service.MemberLoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberLoginServiceImpl implements MemberLoginService {

    final RedisTemplate<String, String> redisTemplate;

    @Override
    public String getClientId() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /***
     * 获取图形验证码
     * @param form
     * @return
     */
    @Override
    public String getBase64Code(GetBase64CodeRequest form) {
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(300, 192, 5, 1000);
        String code = lineCaptcha.getCode();
        // 将图形验证码写到redis缓存中
        redisTemplate.opsForValue().set(RedisKeyContant.GRAPHIC_VERIFICATION_CODE + form.getClientId(), code, 15, TimeUnit.MINUTES);
        return lineCaptcha.getImageBase64();
    }
}

package com.yangge.finance.biz.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import com.yangge.finance.biz.dto.form.GetBase64CodeRequest;
import com.yangge.finance.biz.service.MemberLoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberLoginServiceImpl implements MemberLoginService {
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
        // todo 将code保存到redis中
        return lineCaptcha.getImageBase64();
    }
}

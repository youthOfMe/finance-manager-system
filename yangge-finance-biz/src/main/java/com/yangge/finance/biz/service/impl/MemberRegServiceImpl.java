package com.yangge.finance.biz.service.impl;

import com.yangge.common.exception.ParameterException;
import com.yangge.finance.biz.domain.MemberBindPhone;
import com.yangge.finance.biz.dto.form.PhoneRegisterForm;
import com.yangge.finance.biz.dto.form.SmsCodeResult;
import com.yangge.finance.biz.enums.SmsCodeTypeEnum;
import com.yangge.finance.biz.service.MemberLoginService;
import com.yangge.finance.biz.service.MemberRegService;
import com.yangge.mybatis.help.MyBatisWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.yangge.finance.biz.domain.MemberBindPhoneField.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberRegServiceImpl implements MemberRegService {

    final MemberLoginService memberLoginService;

    /**
     * 根据手机号进行注册
     *
     * @param request
     * @return
     */
    @Override
    public Long phoneReg(PhoneRegisterForm request) {
        if (!Objects.equals(request.getPassword(), request.getConfirmPassword())) {
            throw new ParameterException("两次输入的密码不一致");
        }
        memberLoginService.checkSmsCode(request.getPhone(), request.getSmsCode(), SmsCodeTypeEnum.REG.getCode());



    }
}

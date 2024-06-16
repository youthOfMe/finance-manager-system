package com.yangge.finance.biz.service;

import com.yangge.finance.biz.dto.form.PhoneRegisterForm;

public interface MemberRegService {

    /**
     * 根据手机号获取用户信息
     *
     * @param form
     * @return
     */
    Long phoneReg(PhoneRegisterForm form);
}

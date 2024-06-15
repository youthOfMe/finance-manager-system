package com.yangge.finance.biz.service;

import com.yangge.finance.biz.dto.form.GetBase64CodeRequest;
import com.yangge.finance.biz.dto.form.GetSmsCodeForm;

public interface MemberLoginService {

    /**
     * 获取客户端ID
     * @return
     */
    String getClientId();

    /**
     * 获取图形验证码
     * @return
     */
    String getBase64Code(GetBase64CodeRequest form);

    /**
     * 获取短信验证码
     * @param form
     */
    void sendSmsCode(GetSmsCodeForm form);

    /**
     * 校验短信验证码是否正确
     * @param phone
     * @param smsCode
     * @param smsCodeType
     * @return
     */
    boolean checkSmsCode(String phone, String smsCode, String smsCodeType);

    /**
     * 校验图形验证码
     *
     * @param clientId
     * @return
     */
    boolean checkBase64Code(String clientId, String code);
}

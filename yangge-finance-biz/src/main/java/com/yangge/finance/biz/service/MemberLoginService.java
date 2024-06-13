package com.yangge.finance.biz.service;

import com.yangge.finance.biz.dto.form.GetBase64CodeRequest;

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
}

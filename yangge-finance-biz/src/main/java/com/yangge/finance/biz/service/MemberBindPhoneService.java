package com.yangge.finance.biz.service;

import com.yangge.finance.biz.domain.MemberBindPhone;

public interface MemberBindPhoneService {
    /**
     * 根据手机号获取用户信息
     *
     * @param phone
     * @return
     */
    MemberBindPhone getMemberByPhone(String phone);

    /**
     * 注册绑定手机号
     *
     * @param phone
     * @param id
     * @param password
     */
    boolean reg(String phone, long id, String password);
}

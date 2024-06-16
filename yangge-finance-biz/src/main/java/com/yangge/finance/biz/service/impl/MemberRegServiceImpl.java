package com.yangge.finance.biz.service.impl;

import com.yangge.common.exception.BizException;
import com.yangge.common.exception.ParameterException;
import com.yangge.finance.biz.constant.RedisKeyConstant;
import com.yangge.finance.biz.domain.MemberBindPhone;
import com.yangge.finance.biz.dto.form.PhoneRegisterForm;
import com.yangge.finance.biz.enums.SmsCodeTypeEnum;
import com.yangge.finance.biz.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberRegServiceImpl implements MemberRegService {

    final MemberLoginService memberLoginService;
    final RedissonClient redissonClient;
    final MemberBindPhoneService memberBindPhoneService;
    final TransactionTemplate transactionTemplate;
    final TenantService tenantService;
    final MemberService memberService;


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

        RLock rLock = redissonClient.getLock(RedisKeyConstant.PHONE_CHANGE + request.getPhone());
        try {
            rLock.lock();
            MemberBindPhone memberBindPhone = memberBindPhoneService.getMemberByPhone(request.getPhone());
            if (memberBindPhone != null) {
                log.warn("手机号: {}已注册", request.getPhone());
                throw new BizException("手机号已注册");
            }
            Long memberId = transactionTemplate.execute(transactionStatus -> {
                long tenantId = tenantService.add();
                long id = memberService.reg(tenantId);
                if (id <= 0) {
                    throw new BizException("注册异常");
                }
                memberBindPhoneService.reg(request.getPhone(), id, request.getPassword());
                return id;
            });
            if (memberId == null) {
                throw new BizException("注册失败");
            }
            return memberId;
        } catch (Exception ex) {
            throw new BizException("注册失败", ex);
        } finally {
            rLock.unlock();
        }
    }
}

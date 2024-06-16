package com.yangge.finance.biz.service.impl;

import com.yangge.finance.biz.constant.CommonConstant;
import com.yangge.finance.biz.domain.Member;
import com.yangge.finance.biz.mapper.MemberMapper;
import com.yangge.finance.biz.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    final MemberMapper memberMapper;

    /**
     * 注册
     * @param tenantId 租户id
     * @return
     */
    @Override
    public long reg(long tenantId) {
        Member member = new Member();
        member.setTenantId(tenantId);
        member.setSysRoleIds("[" + CommonConstant.ROLE_MEMBER + "]");
        member.initDefault();
        memberMapper.insert(member);

        return member.getId();
    }
}

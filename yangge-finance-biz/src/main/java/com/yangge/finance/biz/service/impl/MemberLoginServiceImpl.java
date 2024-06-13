package com.yangge.finance.biz.service.impl;

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
}

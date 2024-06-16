package com.yangge.finance.admin.api.controller;

import com.yangge.common.dto.ApiResponse;
import com.yangge.finance.biz.dto.form.PhoneRegisterForm;
import com.yangge.finance.biz.service.MemberRegService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "注册模块")
@RestController
@RequestMapping("/reg")
@RequiredArgsConstructor
@Slf4j
public class RegController {

    final MemberRegService memberRegService;

    @ApiOperation("手机号注册")
    @PostMapping("/phoneReg")
    public ApiResponse<Long> phoneReg(@Validated @RequestBody PhoneRegisterForm form) {
        return ApiResponse.success(memberRegService.phoneReg(form));
    }

}

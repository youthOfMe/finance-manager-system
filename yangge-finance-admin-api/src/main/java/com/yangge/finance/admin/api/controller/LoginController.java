package com.yangge.finance.admin.api.controller;

import com.yangge.common.dto.ApiResponse;
import com.yangge.finance.biz.dto.form.GetBase64CodeRequest;
import com.yangge.finance.biz.dto.form.GetSmsCodeForm;
import com.yangge.finance.biz.service.MemberLoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "用户登录模块")
@RestController
@RequestMapping(value = "/login")
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    final MemberLoginService memberLoginService;

    @ApiOperation(value = "获取客户端ID")
    @GetMapping("/getClientId")
    public ApiResponse<String> getClientId() {
        String result = memberLoginService.getClientId();
        return ApiResponse.success(result);
    }

    @ApiOperation("获取图形验证码")
    @GetMapping("/getBase64Code")
    public ApiResponse<String> getBase64Code(@Validated @ModelAttribute GetBase64CodeRequest form) {
        return ApiResponse.success(memberLoginService.getBase64Code(form));
    }

    @ApiOperation("获取短信验证码")
    @GetMapping("/sendSmsCode")
    public ApiResponse<Void> sendSmsCode(@Validated @ModelAttribute GetSmsCodeForm form) {
        memberLoginService.sendSmsCode(form);
        return ApiResponse.success();
    }
}

package com.yangge.finance.admin.api.controller;

import com.yangge.common.dto.ApiResponse;
import com.yangge.finance.biz.service.MemberLoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
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
}
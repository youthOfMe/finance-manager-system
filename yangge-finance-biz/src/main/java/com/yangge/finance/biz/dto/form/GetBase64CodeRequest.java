package com.yangge.finance.biz.dto.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class GetBase64CodeRequest {

    /**
     * 客户端ID
     */
    @ApiModelProperty("客户端ID")
    @NotBlank(message = "客户端ID不能为空")
    @Pattern(regexp = "^[0-9A-Za-z]{6,32}$", message = "clientId非法")
    private String clientId;
}

package com.teros.api_control_manager.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonResult {

    @ApiModelProperty(value = "응답 성공여부 : true/false")
    private boolean success;

    @ApiModelProperty(value = "Reason Code => 정상 : = 0")
    private int ReasonCode;

    @ApiModelProperty(value = "ResonMessage")
    private String ReasonMessage;

    @ApiModelProperty(value = "AppendData")
    private String appendData;
}
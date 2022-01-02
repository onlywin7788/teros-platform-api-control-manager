package com.teros.api_control_manager.controller.api.v1.config;

import com.teros.api_control_manager.model.response.CommonResult;
import com.teros.api_control_manager.service.response.ResponseService;
import com.teros.ext.common.file.CommonFile;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.management.monitor.CounterMonitor;

@Api(tags = {"1-1. config : deploy"})
@RestController
@RequestMapping(value = "/v1")
public class ConfigRestController {

    @Value("${extra.env.teros_home}")
    private String terosHomeEnv;

    private final ResponseService responseService;

    public ConfigRestController(ResponseService responseService) {
        this.responseService = responseService;
    }

    @PostMapping(value = "/config/deploy")
    public CommonResult deployConfig(@RequestParam String configName, @RequestBody String configContents) throws Exception{

        CommonFile commonFile = new CommonFile();
        String configFilePath = String.format("%s/api-service/config/%s.json", terosHomeEnv, configName);

        commonFile.writeFile(configFilePath, configContents);

        return responseService.getResult(true);
    }
}

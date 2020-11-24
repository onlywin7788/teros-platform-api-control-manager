package com.teroa.api_control_manager.service.request.router;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Component
@FeignClient(name = "teros-api-control-manager")
public interface RequestRouteService {

    @GetMapping("/v1/request/manager/routes{request_uid}")
    String getRouteList(@PathVariable String request_uid, HttpServletResponse response);
}
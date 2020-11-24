package com.teroa.api_control_manager.controller.v1.Router;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/v1")
public class TestController {


    @GetMapping(value = "/test/{request_uid}", produces = "application/json; charset=UTF-8")
    public String getRouteList(@PathVariable String request_uid) {

        String requestURL = "http://10.10.2.102:38081/v1/request/manager/routes/" + request_uid;

        ResponseEntity<String> response = requestRouter(requestURL);

        int statusCode = response.getStatusCode().value();
        if (statusCode == 200) {

            String responseUid = response.getHeaders().getFirst("router-request-uid");
            log.info("reqeust uid : " + responseUid);
            log.info("=================== REQUEST BODY ====================");
            log.info(response.getBody());


        } else if (statusCode == 204) {
            log.info(String.format("Router List already applied / ROUTER_ID : [%s]", request_uid));
        } else {
            log.info("unknown http status code : " + statusCode);
        }

        return statusCode + "";
    }

    public ResponseEntity<String> requestRouter(String url) {
        MultiValueMap<String, String> headerMap = new LinkedMultiValueMap<>();
        HttpEntity<?> headers = new HttpEntity<>(headerMap);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> responseEntity = restTemplate.exchange(url,
                HttpMethod.GET, headers, String.class);

        return responseEntity;
    }

}
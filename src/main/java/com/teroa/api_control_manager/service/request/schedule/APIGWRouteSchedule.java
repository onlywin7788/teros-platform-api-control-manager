package com.teroa.api_control_manager.service.request.schedule;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.teroa.api_control_manager.service.request.router.RequestRouteServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

@Service
@Slf4j
public class APIGWRouteSchedule {

    private final RequestRouteServiceImpl requestRouteServiceImpl;

    @Value("${extra.env.teros_home}")
    private String baseHome;

    private String configRouterFilePath;

    public APIGWRouteSchedule(RequestRouteServiceImpl requestRouteServiceImpl) {
        this.requestRouteServiceImpl = requestRouteServiceImpl;
    }

    @Scheduled(fixedDelay = 3000)
    public void createRouteFile() throws Exception {

        setConfigRoutePath();

        String requestUid = "dummy";
        String requestURL = "http://10.10.2.102:38081/v1/request/manager/routes/";

        if(fileExist(configRouterFilePath) == true)
        {
            String contents = readFile(configRouterFilePath);
            requestUid = searchRequestRouteUid(contents);
        }
        requestURL += requestUid;

        ResponseEntity<String> response = requestRouter(requestURL);
        int statusCode = response.getStatusCode().value();

        if (statusCode == 200) {
            String responseUid = response.getHeaders().getFirst("router-request-uid");
            log.info("reqeust uid : " + responseUid);
            log.info("=================== REQUEST BODY ====================");

            String receiveRouteBody = response.getBody();
            String ruleTxt = requestRouteServiceImpl.createRoute(responseUid, receiveRouteBody);

            writeFile(configRouterFilePath, ruleTxt);
            log.info(ruleTxt);

        } else if (statusCode == 204) {
            ;//log.info(String.format("Router List already applied / ROUTER_ID : [%s]", requestUid));
        } else {
            log.info("unknown http status code : " + statusCode);
        }
    }

    public void setConfigRoutePath()
    {
        configRouterFilePath = baseHome + File.separator + "config"
                + File.separator + "api-gateway" + File.separator + "api-gateway-router.json";
    }

    public ResponseEntity<String> requestRouter(String url) {
        MultiValueMap<String, String> headerMap = new LinkedMultiValueMap<>();
        HttpEntity<?> headers = new HttpEntity<>(headerMap);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> responseEntity = restTemplate.exchange(url,
                HttpMethod.GET, headers, String.class);

        return responseEntity;
    }

    public void writeFile(String filePath, String contents) {

        try {
            FileOutputStream outputStream = new FileOutputStream(filePath);
            byte[] strToBytes = contents.getBytes();
            outputStream.write(strToBytes);
            outputStream.close();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public boolean fileExist(String filePath)
    {
        if(Files.exists(Paths.get(filePath)))
            return true;
        else
            return false;
    }


    public String readFile(String ConfigPath)
    {
        String contents = "";
        try {
            File file = new File(ConfigPath);
            contents = FileUtils.readFileToString(file, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contents;
    }

    public String searchRequestRouteUid(String contents)
    {
        JsonParser Parser = new JsonParser();
        JsonObject jsonObj = (JsonObject) Parser.parse(contents);
        return jsonObj.get("requestRouteUid").getAsString();
    }
}

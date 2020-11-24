package com.teroa.api_control_manager.controller.v1.Router;

import com.teroa.api_control_manager.model.RouteListModel;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**************************

 {
 "predicates":[
 {
 "name":"Path",
 "args":{
 "app1":"/app400"
 }
 },
 {
 "name":"Method",
 "args":{
 "method":"GET"
 }
 }
 ],
 "filters":[
 "RewritePath=/app400,/APIC/DATA.json"
 ],
 "uri":"http://10.10.2.250:8080"
 }

**************************/

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/v1")
public class RouterController {

    private final GatewayService gatewayService;

    public RouterController(GatewayService gatewayService) {
        this.gatewayService = gatewayService;
    }

    @GetMapping(value = "/api-gw/refresh")
    public RouteListModel getApps() {
        /*
        return gatewayService.get();

        */

        return null;
    }
}
package com.teroa.api_control_manager.service;

import com.teroa.api_control_manager.model.RouteListModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GatewayService {

    public RouteListModel createRouteList(String routeInfo){

        RouteListModel routeListModel = RouteListModel.builder()
                .build();

        // set id
        routeListModel.setId("TEST");

        // set predicates
        HashMap predicatesMap = new HashMap();
        HashMap predicatesArgsMap = new HashMap();
        List predicatesList = new ArrayList();

        predicatesMap.put("name", "Method");
        predicatesArgsMap.put("type", "GET");
        predicatesMap.put("args", predicatesArgsMap);

        predicatesList.add(predicatesMap);
        routeListModel.setPredicates(predicatesList);

        // set filter
        List<String> filterList = new ArrayList<String>();
        filterList.add("RewritePath=/app500,/APIC/DATA.json");
        routeListModel.setFilters(filterList);

        // set uri
        routeListModel.setUri("http://10.10.2.250:8080");

        return routeListModel;
    }





}

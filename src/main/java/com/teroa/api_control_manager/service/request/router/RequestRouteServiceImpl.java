package com.teroa.api_control_manager.service.request.router;

import com.google.gson.*;
import com.teroa.api_control_manager.model.RouteListModel;
import com.teroa.api_control_manager.model.RouteModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class RequestRouteServiceImpl {

    private final RequestRouteService requestRouteService;

    public RequestRouteServiceImpl(RequestRouteService requestRouteService) {
        this.requestRouteService = requestRouteService;
    }


    public String getRouteList(String request_uid, HttpServletResponse response)
    {
        String body =  requestRouteService.getRouteList(request_uid, response);
        return body;
    }



    public String createRoute(String requestRouteUid, String jsonString) throws Exception {

        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        JsonParser Parser = new JsonParser();
        JsonObject jsonObj = (JsonObject) Parser.parse(jsonString);
        JsonArray routeArray = (JsonArray) jsonObj.get("list");
        Map<String, String> requestRouterMap = new HashMap<String, String>();

        RouteModel routeModel = new RouteModel();
        ArrayList<RouteListModel> arrayListRouteListModelRule = new ArrayList<>();

        routeModel.setRequestRouteUid(requestRouteUid);

        for (int i = 0; i < routeArray.size(); i++) {

            requestRouterMap.clear();
            JsonObject object = (JsonObject) routeArray.get(i);

            requestRouterMap.put("routeId", object.get("routeId").getAsString());
            requestRouterMap.put("method", object.get("method").getAsString());
            requestRouterMap.put("sourceUri", object.get("sourceUri").getAsString());
            requestRouterMap.put("targetUri", object.get("targetUri").getAsString());
            requestRouterMap.put("targetUrl", object.get("targetUrl").getAsString());

            RouteListModel routeListModel = createRouteRule(requestRouterMap);
            arrayListRouteListModelRule.add(routeListModel);
        }

        routeModel.setRouteList(arrayListRouteListModelRule);
        return gson.toJson(routeModel);
    }

    public RouteListModel createRouteRule(Map<String, String> requestRouterRule){

        String routeId = requestRouterRule.get("routeId");
        String method = requestRouterRule.get("method");
        String sourceUri = requestRouterRule.get("sourceUri");
        String targetUri = requestRouterRule.get("targetUri");
        String targetUrl = requestRouterRule.get("targetUrl");

        RouteListModel routeListModel = RouteListModel.builder()
                .build();

        // set id
        routeListModel.setId(routeId);

        // set predicates
        HashMap predicatesPathMap = new HashMap();
        HashMap predicatesPathArgsMap = new HashMap();
        HashMap predicatesMethodMap = new HashMap();
        HashMap predicatesMethodArgsMap = new HashMap();
        List predicatesList = new ArrayList();

        // path
        predicatesPathMap.put("name", "Path");
        predicatesPathArgsMap.put("_genkey_0", sourceUri);
        predicatesPathMap.put("args", predicatesPathArgsMap);
        predicatesList.add(predicatesPathMap);

        // method
        predicatesMethodMap.put("name", "Method");
        predicatesMethodArgsMap.put("type", method);
        predicatesMethodMap.put("args", predicatesMethodArgsMap);
        predicatesList.add(predicatesMethodMap);

        routeListModel.setPredicates(predicatesList);

        // set filter
        List<String> filterList = new ArrayList<String>();
        filterList.add(String.format("RewritePath=%s,%s",sourceUri,targetUri));
        routeListModel.setFilters(filterList);

        // set uri
        routeListModel.setUri(targetUrl);

        return routeListModel;
    }
}

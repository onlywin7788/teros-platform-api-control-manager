package com.teroa.api_control_manager.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class RouteModel {
    private String requestRouteUid;
    private ArrayList<RouteListModel> routeList;
}
package com.teroa.api_control_manager.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;

@Getter
@Setter
public class Route {
    private String id;
    private List predicates;
    private List<String> filters;
    private String uri;

    @Builder
    public Route() {
    }
}
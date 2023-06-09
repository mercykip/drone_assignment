package com.example.demo.api;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

public class DroneUtils {
    public static List<String> filterRequestParams(HttpServletRequest request, List<String> knownParams) {
        Enumeration<String> query = request.getParameterNames();
        List<String> list = Collections.list(query);
        list.removeAll(knownParams);
        return list;
    }
}
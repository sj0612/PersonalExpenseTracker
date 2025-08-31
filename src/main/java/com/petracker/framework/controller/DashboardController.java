package com.petracker.framework.controller;

import com.petracker.framework.dto.CategoryAmountDTO;
import com.petracker.framework.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {
    @Autowired
    DashboardService dashboardService;
    @GetMapping("/getCategoryWiseAmount/{chart}")
    public List<Object[]> getGraphValues(@PathVariable("chart") String chartName){
        return dashboardService.getGraphValues(chartName);
    }
}

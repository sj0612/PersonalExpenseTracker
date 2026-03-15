package com.petracker.framework.service;

import com.petracker.framework.repository.AggregationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DashboardService {
    @Autowired
    public AggregationRepository aggregationRepository;
    @Autowired
    public UserService userService;

    public List<Object[]> getGraphValues(String chartName){
        Long userId = userService.getCurrentUser().getUserId();
        switch(chartName){
            case "CategorySums":
                return aggregationRepository.findCategorySums(userId);
            case "MonthlySummary":
                return aggregationRepository.findMonthlySummary(userId);
            default:
                return Collections.emptyList();
        }
    }

    public List<Object[]> monthWiseSummaryValue() {
        return aggregationRepository.findMonthlySummary(userService.getCurrentUser().getUserId());
    }
}

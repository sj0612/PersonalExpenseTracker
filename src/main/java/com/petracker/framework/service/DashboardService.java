package com.petracker.framework.service;

import com.petracker.framework.dto.CategoryAmountDTO;
import com.petracker.framework.models.Category;
import com.petracker.framework.repository.AggregationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DashboardService {
    @Autowired
    public AggregationRepository aggregationRepository;
    public List<Object[]> getGraphValues(String chartName){
        switch(chartName){
            case "CategorySums":
                return aggregationRepository.findCategorySums();
            case "MonthlySummary":
                return aggregationRepository.findMonthlySummary();
            default:
                return Collections.emptyList();
        }
    }

    public List<Object[]> monthWiseSummaryValue() {
        return aggregationRepository.findMonthlySummary();
    }
}

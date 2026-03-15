package com.petracker.framework.controller;

import com.petracker.framework.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dashboard")
@Tag(name = "Dashboard", description = "Analytics and reporting APIs for dashboard charts")
@SecurityRequirement(name = "bearerAuth")
public class DashboardController {

    @Autowired
    DashboardService dashboardService;

    @Operation(
            summary = "Get category-wise aggregated amounts",
            description = """
                    Returns aggregated data for the specified chart type.
                    
                    Supported chart values:
                    - `pie` — category-wise spending distribution
                    - `bar` — monthly expense/income bar chart
                    - `line` — trend line over time
                    
                    Each element in the returned array is a `[categoryName, totalAmount]` pair.
                    """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Chart data retrieved successfully",
                    content = @Content(array = @ArraySchema(schema = @Schema(type = "array"))))
    })
    @GetMapping("/getCategoryWiseAmount/{chart}")
    public List<Object[]> getGraphValues(
            @Parameter(description = "Chart type (e.g. MonthlySummary, CategorySums)", required = true, example = "MonthlySummary")
            @PathVariable("chart") String chartName) {
        return dashboardService.getGraphValues(chartName);
    }
}

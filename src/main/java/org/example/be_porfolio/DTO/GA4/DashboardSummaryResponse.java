package org.example.be_porfolio.DTO.GA4;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class DashboardSummaryResponse {
    private DashboardStatsResponse stats;
    private List<GA4Response> chartData;
}

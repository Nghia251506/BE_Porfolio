package org.example.be_porfolio.Service;

import com.google.analytics.data.v1beta.*;
import org.example.be_porfolio.DTO.GA4.GA4Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GA4Service {

    private final BetaAnalyticsDataClient analyticsDataClient;

    @Value("${ga4.property-id}")
    private String propertyId;

    public GA4Service(BetaAnalyticsDataClient analyticsDataClient) {
        this.analyticsDataClient = analyticsDataClient;
    }

    public void getReport() {
        RunReportRequest request = RunReportRequest.newBuilder()
                .setProperty("properties/" + propertyId)
                .addDimensions(Dimension.newBuilder().setName("date")) // Nhóm theo ngày
                .addMetrics(Metric.newBuilder().setName("screenPageViews")) // Lấy số view
                .addDateRanges(DateRange.newBuilder().setStartDate("7daysAgo").setEndDate("today"))
                .build();

        RunReportResponse response = analyticsDataClient.runReport(request);

        System.out.println("Kết quả báo cáo từ GA4:");
        for (Row row : response.getRowsList()) {
            System.out.printf("Ngày: %s - Views: %s%n",
                    row.getDimensionValues(0).getValue(),
                    row.getMetricValues(0).getValue());
        }
    }

    public List<GA4Response> getViewStats() {
        RunReportRequest request = RunReportRequest.newBuilder()
                .setProperty("properties/" + propertyId)
                .addDimensions(Dimension.newBuilder().setName("date"))
                .addMetrics(Metric.newBuilder().setName("screenPageViews"))
                .addDateRanges(DateRange.newBuilder().setStartDate("7daysAgo").setEndDate("today"))
                .build();

        RunReportResponse response = analyticsDataClient.runReport(request);

        return response.getRowsList().stream().map(row -> new GA4Response(
                formatDate(row.getDimensionValues(0).getValue()), // Format ngày ở đây
                Long.parseLong(row.getMetricValues(0).getValue())
        )).collect(Collectors.toList());
    }
    private String formatDate(String dateStr) {
        if (dateStr == null || dateStr.length() < 8) return dateStr;
        return dateStr.substring(6, 8) + "/" + dateStr.substring(4, 6);
    }
}

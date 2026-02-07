package org.example.be_porfolio.Controller;

import org.example.be_porfolio.DTO.GA4.DashboardStatsResponse;
import org.example.be_porfolio.DTO.GA4.DashboardSummaryResponse;
import org.example.be_porfolio.DTO.GA4.GA4Response;
import org.example.be_porfolio.Service.CertificateService;
import org.example.be_porfolio.Service.GA4Service;
import org.example.be_porfolio.Service.Project.ProjectService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final GA4Service ga4Service;
    private final ProjectService projectService;
    private final CertificateService certificateService;

    @GetMapping("/summary")
    public DashboardSummaryResponse getDashboardSummary() {
        // 1. Lấy dữ liệu biểu đồ từ GA4 (Đã map vào DTO GA4Response)
        List<GA4Response> chartData = ga4Service.getViewStats();

        // 2. Tính tổng lượt xem từ dữ liệu biểu đồ để hiển thị trên thẻ Stats
        long totalViews = chartData.stream()
                .mapToLong(GA4Response::getViews)
                .sum();

        // 3. Đóng gói vào DTO Stats
        DashboardStatsResponse stats = DashboardStatsResponse.builder()
                .totalProjects(projectService.countAll())
                .totalCertificates(certificateService.countAll())
                .totalViews(totalViews)
                .totalVisitors(0L) // Có thể bổ sung metric 'activeUsers' vào GA4Service nếu cần
                .build();

        // 4. Trả về Response tổng hợp duy nhất
        return DashboardSummaryResponse.builder()
                .stats(stats)
                .chartData(chartData)
                .build();
    }
}

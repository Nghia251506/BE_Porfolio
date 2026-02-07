package org.example.be_porfolio.DTO.GA4;

import lombok.Builder;
import lombok.Data;

@Data
@Builder // Dùng Builder để khởi tạo cho linh hoạt
public class DashboardStatsResponse {
    private long totalProjects;
    private long totalCertificates;
    private long totalViews;
    private long totalVisitors; // Thêm nếu ông muốn kéo từ GA4
}

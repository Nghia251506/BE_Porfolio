package org.example.be_porfolio.DTO.GA4;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GA4Response {
    private String date;   // Ngày (VD: 20260207)
    private Long views;    // Số lượt xem (Dùng Long để FE tính toán cho dễ)
}

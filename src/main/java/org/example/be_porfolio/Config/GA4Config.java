package org.example.be_porfolio.Config;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.analytics.data.v1beta.BetaAnalyticsDataClient;
import com.google.analytics.data.v1beta.BetaAnalyticsDataSettings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Configuration
public class GA4Config {

    @Value("${ga4.credentials.json}")
    private String credentialsJson;

    @Bean
    public BetaAnalyticsDataClient betaAnalyticsDataClient() throws IOException {
        // Đọc JSON từ String (Biến môi trường)
        GoogleCredentials credentials = GoogleCredentials.fromStream(
                new ByteArrayInputStream(credentialsJson.getBytes(StandardCharsets.UTF_8))
        );

        BetaAnalyticsDataSettings settings = BetaAnalyticsDataSettings.newBuilder()
                .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
                .build();

        return BetaAnalyticsDataClient.create(settings);
    }
}

package com.tenco.web._core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "naver")
@Data
public class NaverProperties {
    private Client client;
    private String redirectUri;

    @Data
    public static class Client {
        private String id;
        private String secret;
    }
}

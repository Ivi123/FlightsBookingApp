package com.example.integrationtests.config;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.mockserver.integration.ClientAndServer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.web.reactive.function.client.WebClient;

@TestConfiguration
public class TestConfig {
    private ClientAndServer taromMockServer;
    private ClientAndServer lufthansaMockServer;

    @Bean
    @Primary
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    @PostConstruct
    public void startTaromMockServer() {
        taromMockServer = ClientAndServer.startClientAndServer(8020);
    }

    @PreDestroy
    public void stopTaromMockServer() {
        if (taromMockServer != null) {
            taromMockServer.stop();
        }
    }

    @PostConstruct
    public void startMockServer() {
        lufthansaMockServer = ClientAndServer.startClientAndServer(8084);
    }

    @PreDestroy
    public void stopMockServer() {
        if (lufthansaMockServer != null) {
            lufthansaMockServer.stop();
        }
    }

    @Bean
    public ClientAndServer lufthansaMockServer() {
        return lufthansaMockServer;
    }

    @Bean
    public ClientAndServer taromMockServer() {
        return taromMockServer;
    }
}

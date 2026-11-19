package br.edu.atitus.bompradev.scheduling.configs;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ConfigCatalogo {
    @Bean
    @LoadBalanced
    WebClient.Builder catalogoWebClient() { return WebClient.builder(); }
}

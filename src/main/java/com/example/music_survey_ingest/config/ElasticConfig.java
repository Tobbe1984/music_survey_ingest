package com.example.music_survey_ingest.config;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponseInterceptor;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticConfig {
    @Value("${client.elasticsearch.host}")
    String elasticHost;
    @Value("${client.elasticsearch.port}")
    Integer elasticPort;

    @Bean
    RestClient restClient() {
        return RestClient.builder(new HttpHost(elasticHost, elasticPort))
            .setHttpClientConfigCallback(httpClientBuilder -> {
                    httpClientBuilder.disableAuthCaching();
                    httpClientBuilder.addInterceptorLast((HttpResponseInterceptor)
                            (response, context) ->
                                    response.addHeader("X-Elastic-Product", "Elasticsearch"));
                    return httpClientBuilder;})
                .build();
    }
}

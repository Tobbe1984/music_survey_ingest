package com.example.music_survey_ingest.config;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticConfig {
    @Bean
    RestClient restClient() {
        return RestClient.builder(new HttpHost("localhost", 9200))
            .setDefaultHeaders(new Header[]{
                    new BasicHeader("Content-type", "application/json")
            }).setHttpClientConfigCallback(httpClientBuilder -> {
                    httpClientBuilder.disableAuthCaching();
                    httpClientBuilder.addInterceptorLast((HttpResponseInterceptor)
                            (response, context) ->
                                    response.addHeader("X-Elastic-Product", "Elasticsearch"));
                    return httpClientBuilder;})
                .build();
    }
}

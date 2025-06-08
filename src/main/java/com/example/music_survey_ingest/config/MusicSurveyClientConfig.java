package com.example.music_survey_ingest.config;

import com.example.music_survey_ingest.client.MusicSurveyClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import org.springframework.web.service.invoker.RestClientAdapter;

@Configuration
public class MusicSurveyClientConfig {

    @Value("${client.music-survey.base-url}")
    private String baseUrl;

    @Bean
    MusicSurveyClient musicSurveyClient(RestClient.Builder builder) {
        RestClient restClient = builder.baseUrl(baseUrl).build();
        HttpServiceProxyFactory factory =
                HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient)).build();
        return factory.createClient(MusicSurveyClient.class);
    }
}

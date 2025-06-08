package com.example.music_survey_ingest.client.config;

import com.example.music_survey_ingest.client.MusicSurveyClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class MusicSurveyClientConfig {
    @Bean
    MusicSurveyClient musicSurveyClient(@Value("${client.music-survey.base-url}") String baseUrl) {
        RestClient restClient = RestClient.builder().baseUrl(baseUrl).build();
        HttpServiceProxyFactory factory =
                HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient)).build();
        return factory.createClient(MusicSurveyClient.class);
    }
}

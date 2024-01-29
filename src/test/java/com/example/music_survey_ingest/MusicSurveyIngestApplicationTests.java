package com.example.music_survey_ingest;

import com.example.music_survey_ingest.common.SurveyIngestElasticsearchContainer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@EnableFeignClients
@Testcontainers
class MusicSurveyIngestApplicationTests {
    @Container
    private static final ElasticsearchContainer elasticsearchContainer = new SurveyIngestElasticsearchContainer();

    @BeforeAll
    static void setUp() {
        elasticsearchContainer.start();
    }

    @Test
    void contextLoads() {
    }

    @AfterAll
    static void destroy() {
        elasticsearchContainer.stop();
    }

}

package com.example.music_survey_ingest;

import com.example.music_survey_ingest.common.SurveyIngestElasticsearchContainer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
class MusicSurveyIngestApplicationIT {
    @Container
    private static final ElasticsearchContainer elasticsearchContainer = new SurveyIngestElasticsearchContainer();

    @DynamicPropertySource
    static void elasticsearchProperties(DynamicPropertyRegistry registry) {
        registry.add("client.elasticsearch.host", elasticsearchContainer::getHost);
        registry.add("client.elasticsearch.port", () -> elasticsearchContainer.getMappedPort(9200));
    }

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

package com.example.music_survey_ingest;

import com.example.music_survey_ingest.common.SurveyIngestElasticsearchContainer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static java.nio.charset.Charset.defaultCharset;
import static org.springframework.util.StreamUtils.copyToString;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
@WireMockTest(httpPort = 9010)
class MusicSurveyIngestApplicationIT {
    @Container
    private static final ElasticsearchContainer elasticsearchContainer = new SurveyIngestElasticsearchContainer();

    @DynamicPropertySource
    static void elasticsearchProperties(DynamicPropertyRegistry registry) {
        registry.add("client.elasticsearch.host", elasticsearchContainer::getHost);
        registry.add("client.elasticsearch.port", () -> elasticsearchContainer.getMappedPort(9200));
    }

    @BeforeAll
    static void setUp() throws IOException {
        elasticsearchContainer.start();
        var musicSurveyJsonResponse = copyToString(
                MusicSurveyIngestIT.class.getClassLoader().getResourceAsStream("payload/music_survey.json"),
                defaultCharset()
        );
        stubFor(WireMock.get("/api/v1/voting")
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(musicSurveyJsonResponse)
                )
        );
    }

    @Test
    void contextLoads() {
    }

    @AfterAll
    static void destroy() {
        elasticsearchContainer.stop();
    }
}

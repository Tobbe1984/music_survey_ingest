package com.example.music_survey_ingest;

import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit.MockServerConfig;
import au.com.dius.pact.consumer.junit5.PactConsumerTest;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.PactSpecVersion;
import au.com.dius.pact.core.model.V4Pact;
import au.com.dius.pact.core.model.annotations.Pact;
import com.example.music_survey_ingest.client.MusicSurveyClientWrapper;
import com.example.music_survey_ingest.client.dto.GenreVotingDto;
import com.example.music_survey_ingest.client.dto.GenreVotingDtoTestHelper;
import com.example.music_survey_ingest.common.SurveyIngestElasticsearchContainer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.junit.jupiter.Container;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("pact")
@EnableFeignClients
@PactTestFor(pactVersion = PactSpecVersion.V4)
@MockServerConfig(hostInterface = "localhost", port = "9009")
@PactConsumerTest
public class MusicSurveyIngestPactIT {
    @Autowired
    MusicSurveyClientWrapper musicSurveyClientWrapper;
    @Container
    private static final ElasticsearchContainer elasticsearchContainer = new SurveyIngestElasticsearchContainer();

    private static final String CONSUMER = "music-survey-ingest";
    private static final String PROVIDER = "music-survey";
    public static final String PROVIDER_REQUEST_URL = "/api/v1/voting";

    private static String musicSurveyJsonResponse;
    private static final List<GenreVotingDto> genreVotingDtos = List.of(GenreVotingDtoTestHelper.builder().build());
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    static void setupAll() throws IOException {
        elasticsearchContainer.start();
        System.setProperty("http.keepAlive", "false");
        musicSurveyJsonResponse = objectMapper.writeValueAsString(genreVotingDtos);
    }

    @Disabled("pact definition")
    @Pact(provider = PROVIDER, consumer = CONSUMER)
    private V4Pact sendGetAllRequest(PactDslWithProvider builder) {
        var givenDescription = "Get all votings";
        var uponDescription = "A GET-request to %s".formatted(PROVIDER_REQUEST_URL);
        return builder
                .given(givenDescription)
                .uponReceiving(uponDescription)
                .method(HttpMethod.GET.name())
                .path(PROVIDER_REQUEST_URL)
                .willRespondWith()
                .status(HttpStatus.OK.value())
                .body(musicSurveyJsonResponse)
                .matchHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .toPact(V4Pact.class);
    }

    @Test
    @PactTestFor(pactMethod = "sendGetAllRequest")
    void test_getAllRequest() {
        assertDoesNotThrow(() -> musicSurveyClientWrapper.loadAll());
    }

    @AfterAll
    static void destroy() {
        elasticsearchContainer.stop();
    }
}

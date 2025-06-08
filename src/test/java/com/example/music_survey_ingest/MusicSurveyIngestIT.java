package com.example.music_survey_ingest;

import com.example.music_survey_ingest.client.MusicSurveyClientWrapper;
import com.example.music_survey_ingest.client.dto.GenreVotingDto;
import com.example.music_survey_ingest.common.SurveyIngestElasticsearchContainer;
import com.example.music_survey_ingest.common.TestDataMockGenerator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static java.nio.charset.Charset.defaultCharset;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.util.StreamUtils.copyToString;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@ActiveProfiles("test")
@WireMockTest(httpPort = 9010)
public class MusicSurveyIngestIT {
    @Container
    private static final ElasticsearchContainer elasticsearchContainer = new SurveyIngestElasticsearchContainer();

    @Autowired
    private MusicSurveyClientWrapper musicSurveyClientWrapper;

    @Autowired
    private MockMvc mockMvc;

    private final String INGEST_BASE_URL = "/api/v1/surveys";
    private final String MUSIC_SURVEY_BASE_URL = "/api/v1/voting";
    private static String musicSurveyJsonResponse;

    private List<GenreVotingDto> genreVotingDtos;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    static void setUpAll() throws IOException {
        elasticsearchContainer.start();
        musicSurveyJsonResponse = copyToString(
                MusicSurveyIngestIT.class.getClassLoader().getResourceAsStream("payload/music_survey.json"),
                defaultCharset()
        );
    }

    @BeforeEach
    void setUp() throws IOException {
        stubFor(WireMock.get(MUSIC_SURVEY_BASE_URL)
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(musicSurveyJsonResponse)
                )
        );
        this.genreVotingDtos = this.objectMapper.readValue(musicSurveyJsonResponse, new TypeReference<List<GenreVotingDto>>(){});
    }

    @Test
    @Order(1)
    void loadAllTest() {
        assertDoesNotThrow(() -> musicSurveyClientWrapper.loadAll());
    }

    @Test
    @Order(2)
    void whenValidRequest_shouldReturnAllVotings() throws Exception {
        this.mockMvc.perform(get("%s?size=6".formatted(INGEST_BASE_URL)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*]").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(this.genreVotingDtos.size())));
    }

    @Test
    @Order(3)
    void whenValidRequestByInstrument_shouldReturnAllVotingsForInstrument() throws Exception {
        var count = this.genreVotingDtos.stream().filter(v -> v.getInstrument().getName().equals(TestDataMockGenerator.INSTRUMENT_NAME)).count();

        this.mockMvc.perform(get("%s/instrument/%s?size=6".formatted(INGEST_BASE_URL, TestDataMockGenerator.INSTRUMENT_NAME)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*]").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize((int) count)));
    }

    @Test
    @Order(4)
    void whenValidRequestByGenre_shouldReturnAllVotingsForGenre() throws Exception {
        var count = this.genreVotingDtos.stream().filter(v -> v.getGenre().getName().equals(TestDataMockGenerator.GENRE_NAME)).count();

        this.mockMvc.perform(get("%s/genre/%s?size=1".formatted(INGEST_BASE_URL, TestDataMockGenerator.GENRE_NAME)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*]").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize((int) count)));
    }

    @AfterAll
    static void destroy() {
        elasticsearchContainer.stop();
    }
}

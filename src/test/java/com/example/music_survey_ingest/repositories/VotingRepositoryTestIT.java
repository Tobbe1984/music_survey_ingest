package com.example.music_survey_ingest.repositories;

import com.example.music_survey_ingest.common.SurveyIngestElasticsearchContainer;
import com.example.music_survey_ingest.common.TestDataMockGenerator;
import com.example.music_survey_ingest.data.Voting;
import com.example.music_survey_ingest.data.VotingTestHelper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Testcontainers
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class VotingRepositoryTestIT {
    @Container
    private static final ElasticsearchContainer elasticsearchContainer = new SurveyIngestElasticsearchContainer();

    @Autowired
    private VotingRepository votingRepository;

    private final Voting votingStub =  VotingTestHelper.builder().build();

    @BeforeAll
    static void setUp() {
        elasticsearchContainer.start();
    }

    @Test
    @Order(1)
    void whenSaveAll_resultNotNull() {
        assertNotNull(votingRepository.saveAll(List.of(votingStub)));
    }

    @Test
    @Order(2)
    void whenQueryAllSurveys_listIsNotEmpty() {
        assertNotEquals(0, votingRepository.findAll(PageRequest.of(0, 10)).getTotalElements());
    }

    @Test
    @Order(3)
    void whenQueryAllSurveysByInstrument_listIsNotEmpty() {
        assertNotEquals(0, votingRepository.findVotingByInstrument(TestDataMockGenerator.INSTRUMENT_NAME, PageRequest.of(0, 10)).getTotalElements());
    }

    @Test
    @Order(4)
    void whenQueryAllSurveysByGenre_listIsNotEmpty() {
        assertNotEquals(0, votingRepository.findVotingByGenre(TestDataMockGenerator.GENRE_NAME, PageRequest.of(0, 10)).getTotalElements());
    }

    @AfterAll
    static void destroy() {
        elasticsearchContainer.stop();
    }
}
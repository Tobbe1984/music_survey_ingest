package com.example.music_survey_ingest.service;

import com.example.music_survey_ingest.common.TestDataMockGenerator;
import com.example.music_survey_ingest.data.Voting;
import com.example.music_survey_ingest.data.VotingTestHelper;
import com.example.music_survey_ingest.dto.SurveyDto;
import com.example.music_survey_ingest.dto.SurveyDtoTestHelper;
import com.example.music_survey_ingest.mapper.SurveyMapper;
import com.example.music_survey_ingest.repositories.VotingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
class VotingServiceTest {
    @Mock
    VotingRepository votingRepositoryMock;
    @Mock
    SurveyMapper surveyMapperMock;
    @InjectMocks
    VotingService votingService;

    private final SurveyDto surveyDtoStub = SurveyDtoTestHelper.builder().build();
    private final Voting votingStub = VotingTestHelper.builder().build();

    @Test
    void getSurveysTest() {
        when(votingRepositoryMock.findAll(Mockito.any(PageRequest.class))).thenReturn(new PageImpl<>(List.of(votingStub)));
        when(surveyMapperMock.map(any(Voting.class))).thenReturn(surveyDtoStub);

        var dtos = votingService.getSurveys(PageRequest.of(0, 10));
        dtos.forEach(d -> SurveyDtoTestHelper.compare(d, votingStub));
    }

    @Test
    void getSurveyByInstrument() {
        when(votingRepositoryMock.findVotingByInstrument(anyString(), Mockito.any(PageRequest.class))).thenReturn(new PageImpl<>(List.of(votingStub)));
        when(surveyMapperMock.map(any(Voting.class))).thenReturn(surveyDtoStub);

        var dtos = votingService.getSurveyByInstrument(TestDataMockGenerator.INSTRUMENT_NAME, PageRequest.of(0, 10));
        dtos.forEach(d -> SurveyDtoTestHelper.compare(d, votingStub));
    }

    @Test
    void getSurveyByGenre() {
        when(votingRepositoryMock.findVotingByGenre(anyString(), Mockito.any(PageRequest.class))).thenReturn(new PageImpl<>(List.of(votingStub)));
        when(surveyMapperMock.map(any(Voting.class))).thenReturn(surveyDtoStub);

        var dtos = votingService.getSurveyByGenre(TestDataMockGenerator.GENRE_NAME, PageRequest.of(0, 10));
        dtos.forEach(d -> SurveyDtoTestHelper.compare(d, votingStub));
    }

    @Test
    void saveTest() {
        when(votingRepositoryMock.saveAll(anyList())).thenReturn(List.of(votingStub));
        when(surveyMapperMock.map(any(Voting.class))).thenReturn(surveyDtoStub);
        when(surveyMapperMock.map(any(SurveyDto.class))).thenReturn(votingStub);

        var dtos = votingService.save(List.of(surveyDtoStub));
        dtos.forEach(d -> SurveyDtoTestHelper.compare(d, votingStub));
    }
}
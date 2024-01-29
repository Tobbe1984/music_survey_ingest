package com.example.music_survey_ingest.controller;

import com.example.music_survey_ingest.common.TestDataMockGenerator;
import com.example.music_survey_ingest.dto.SurveyDto;
import com.example.music_survey_ingest.dto.SurveyDtoTestHelper;
import com.example.music_survey_ingest.service.VotingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(IngestController.class)
class IngestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    VotingService votingServiceMock;

    private final SurveyDto surveyDtoStub = SurveyDtoTestHelper.builder().build();

    private final String BASE_URL = "/api/v1/surveys";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void whenValidRequest_shouldReturnAllSurveys() throws Exception {
        // given
        var surveyDtoListStubJsonString = objectMapper.writeValueAsString(List.of(surveyDtoStub));

        // when
        when(votingServiceMock.getSurveys(any(PageRequest.class))).thenReturn(List.of(surveyDtoStub));

        // then
        this.mockMvc.perform(get("%s?size=10".formatted(BASE_URL)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(surveyDtoListStubJsonString)));
    }

    @Test
    void whenValidRequestWithInstrument_shouldReturnAllSurveys() throws Exception {
        // given
        var surveyDtoListStubJsonString = objectMapper.writeValueAsString(List.of(surveyDtoStub));

        // when
        when(votingServiceMock.getSurveyByInstrument(anyString(), any(PageRequest.class))).thenReturn(List.of(surveyDtoStub));

        // then
        this.mockMvc.perform(get("%s/instrument/%s?size=10".formatted(BASE_URL, TestDataMockGenerator.INSTRUMENT_NAME)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(surveyDtoListStubJsonString)));
    }

    @Test
    void whenValidRequestWithGenre_shouldReturnAllSurveys() throws Exception {
        // given
        var surveyDtoListStubJsonString = objectMapper.writeValueAsString(List.of(surveyDtoStub));

        // when
        when(votingServiceMock.getSurveyByGenre(anyString(), any(PageRequest.class))).thenReturn(List.of(surveyDtoStub));

        // then
        this.mockMvc.perform(get("%s/genre/%s?size=10".formatted(BASE_URL, TestDataMockGenerator.GENRE_NAME)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(surveyDtoListStubJsonString)));
    }

}
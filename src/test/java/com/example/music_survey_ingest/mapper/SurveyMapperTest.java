package com.example.music_survey_ingest.mapper;

import com.example.music_survey_ingest.data.Voting;
import com.example.music_survey_ingest.data.VotingTestHelper;
import com.example.music_survey_ingest.dto.SurveyDto;
import com.example.music_survey_ingest.dto.SurveyDtoTestHelper;
import org.junit.jupiter.api.Test;

class SurveyMapperTest {
    private final SurveyMapper surveyMapper = new SurveyMapper();

    private final SurveyDto surveyDtoStub = SurveyDtoTestHelper.builder().build();
    private final Voting votingStub = VotingTestHelper.builder().build();

    @Test
    void whenGivenDto_EntityIsMappedCorrectly() {
        var result = surveyMapper.map(surveyDtoStub);
        SurveyDtoTestHelper.compare(surveyDtoStub, result);
    }

    @Test
    void whenGivenEntity_DtoIsMappedCorrectly() {
        var result = surveyMapper.map(votingStub);
        SurveyDtoTestHelper.compare(result, votingStub);
    }
}
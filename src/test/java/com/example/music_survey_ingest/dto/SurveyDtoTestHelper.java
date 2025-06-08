package com.example.music_survey_ingest.dto;

import com.example.music_survey_ingest.common.TestDataMockGenerator;
import com.example.music_survey_ingest.data.Voting;
import lombok.AllArgsConstructor;
import lombok.With;

import static org.junit.jupiter.api.Assertions.assertEquals;

@AllArgsConstructor
@With
public class SurveyDtoTestHelper {
    private String instrument;
    private String genre;
    private Short voting;

    public static SurveyDtoTestHelper builder() {
        return new SurveyDtoTestHelper(
                TestDataMockGenerator.INSTRUMENT_NAME,
                TestDataMockGenerator.GENRE_NAME,
                TestDataMockGenerator.VOTING
        );
    }

    public SurveyDto build() {
        return new SurveyDto(
            this.instrument,
            this.genre,
            this.voting
        );
    }

    public static void compare(SurveyDto surveyDto, Voting voting) {
        assertEquals(surveyDto.getInstrument(), voting.getInstrument());
        assertEquals(surveyDto.getGenre(), voting.getGenre());
        assertEquals(surveyDto.getVoting(), voting.getVoting());
    }
}

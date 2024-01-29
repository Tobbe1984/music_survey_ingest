package com.example.music_survey_ingest.client.dto;

import com.example.music_survey_ingest.common.TestDataMockGenerator;
import lombok.AllArgsConstructor;
import lombok.With;

@AllArgsConstructor
@With
public class GenreVotingDtoTestHelper {
    private InstrumentDto instrumentDto;
    private GenreDto genreDto;
    private Short value;

    public static GenreVotingDtoTestHelper builder() {
        return new GenreVotingDtoTestHelper(
                new InstrumentDto(TestDataMockGenerator.ID, TestDataMockGenerator.INSTRUMENT_NAME),
                new GenreDto(TestDataMockGenerator.ID, TestDataMockGenerator.GENRE_NAME),
                TestDataMockGenerator.VOTING
        );
    }

    public GenreVotingDto build() {
        return new GenreVotingDto(
            this.instrumentDto,
            this.genreDto,
            this.value
        );
    }
}
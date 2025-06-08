package com.example.music_survey_ingest.data;

import com.example.music_survey_ingest.client.dto.GenreVotingDto;
import com.example.music_survey_ingest.common.TestDataMockGenerator;
import lombok.AllArgsConstructor;
import lombok.With;

import static org.junit.jupiter.api.Assertions.assertEquals;

@AllArgsConstructor
@With
public class VotingTestHelper {
    private String id;
    private String instrument;
    private String genre;
    private Short voting;

    public static VotingTestHelper builder() {
        return new VotingTestHelper(
                TestDataMockGenerator.VOTING_ID,
                TestDataMockGenerator.INSTRUMENT_NAME,
                TestDataMockGenerator.GENRE_NAME,
                TestDataMockGenerator.VOTING
        );
    }

    public Voting build() {
        return new Voting(
                this.id,
                this.instrument,
                this.genre,
                this.voting
        );
    }

    public static void compare(Voting voting, GenreVotingDto genreVotingDto) {
        assertEquals(voting.getInstrument(), genreVotingDto.getInstrument().getName());
        assertEquals(voting.getGenre(), genreVotingDto.getGenre().getName());
        assertEquals(voting.getVoting(), genreVotingDto.getValue());
    }
}

package com.example.music_survey_ingest.mapper;

import com.example.music_survey_ingest.client.dto.GenreVotingDto;
import com.example.music_survey_ingest.client.dto.GenreVotingDtoTestHelper;
import com.example.music_survey_ingest.data.VotingTestHelper;
import org.junit.jupiter.api.Test;

class VotingMapperTest {
    private final VotingMapper votingMapper = new VotingMapper();
    private final GenreVotingDto genreVotingDtoStub =  GenreVotingDtoTestHelper.builder().build();

    @Test
    void whenGivenDto_EntityIsMappedCorrectly() {
        var result = votingMapper.map(genreVotingDtoStub);
        VotingTestHelper.compare(result, genreVotingDtoStub);
    }
}
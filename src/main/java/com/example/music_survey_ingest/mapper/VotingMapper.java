package com.example.music_survey_ingest.mapper;

import com.example.music_survey_ingest.client.dto.GenreVotingDto;
import com.example.music_survey_ingest.data.Voting;

public class VotingMapper {
    public Voting map(GenreVotingDto genreVotingDto) {
        var voting = new Voting();
        voting.setInstrument(genreVotingDto.getInstrument().getName());
        voting.setGenre(genreVotingDto.getGenre().getName());
        voting.setVoting(genreVotingDto.getValue());

        return voting;
    }
}

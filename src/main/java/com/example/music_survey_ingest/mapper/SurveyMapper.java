package com.example.music_survey_ingest.mapper;

import com.example.music_survey_ingest.data.Voting;
import com.example.music_survey_ingest.dto.SurveyDto;
import org.springframework.stereotype.Service;

@Service
public class SurveyMapper {
    public SurveyDto map(Voting voting) {
        var surveyDTO = new SurveyDto();
        surveyDTO.setInstrument(voting.getInstrument());
        surveyDTO.setGenre(voting.getGenre());
        surveyDTO.setVoting(voting.getVoting());

        return surveyDTO;
    }

    public Voting map(SurveyDto surveyDTO) {
        var voting = new Voting();
        voting.setInstrument(surveyDTO.getInstrument());
        voting.setGenre(surveyDTO.getGenre());
        voting.setVoting(surveyDTO.getVoting());

        return voting;
    }
}

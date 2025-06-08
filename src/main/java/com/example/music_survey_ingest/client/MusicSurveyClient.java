package com.example.music_survey_ingest.client;

import com.example.music_survey_ingest.client.dto.GenreVotingDto;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.List;

@HttpExchange
public interface MusicSurveyClient {
    @GetExchange("/api/v1/voting")
    List<GenreVotingDto> getAll();
}

package com.example.music_survey_ingest.client;

import com.example.music_survey_ingest.client.dto.GenreVotingDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "music-survey-client", url = "${client.music-survey.base-url}")
public interface MusicSurveyClient {
    @GetMapping("/api/v1/voting")
    List<GenreVotingDto> getAll();
}

package com.example.music_survey_ingest.controller;

import com.example.music_survey_ingest.dto.SurveyDto;
import com.example.music_survey_ingest.service.VotingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/v1/surveys")
@RequiredArgsConstructor
public class IngestController {
    private final VotingService votingService;

    @GetMapping("")
    @ResponseBody
    private List<SurveyDto> getSurveys(@RequestParam("size") int size) {
        return votingService.getSurveys(Pageable.ofSize(size));
    }

    @GetMapping("/instrument/{instrument}")
    @ResponseBody
    private List<SurveyDto> getSurveysByInstrument(@PathVariable String instrument, @RequestParam("size") int size) {
        return votingService.getSurveyByInstrument(instrument, Pageable.ofSize(size));
    }

    @GetMapping("/genre/{genre}")
    @ResponseBody
    private List<SurveyDto> getSurveysByGenre(@PathVariable String genre, @RequestParam("size") int size) {
        return votingService.getSurveyByGenre(genre, Pageable.ofSize(size));
    }
}

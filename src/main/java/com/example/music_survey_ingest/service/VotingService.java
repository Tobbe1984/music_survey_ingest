package com.example.music_survey_ingest.service;

import com.example.music_survey_ingest.dto.SurveyDto;
import com.example.music_survey_ingest.mapper.SurveyMapper;
import com.example.music_survey_ingest.repositories.VotingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class VotingService {

    private final VotingRepository votingRepository;
    private final SurveyMapper surveyMapper;

    public List<SurveyDto> getSurveys(Pageable pageable) {
        var surveyPage = votingRepository.findAll(pageable);
        return surveyPage.get().map(surveyMapper::map).collect(Collectors.toList());

    }

    public List<SurveyDto> getSurveyByInstrument(String instrument, Pageable pageable) {
        var surveyPage = votingRepository.findVotingByInstrument(instrument, pageable);
        return surveyPage.get().map(surveyMapper::map).collect(Collectors.toList());
    }

    public List<SurveyDto> getSurveyByGenre(String genre, Pageable pageable) {
        var surveyPage = votingRepository.findVotingByGenre(genre, pageable);
        return surveyPage.get().map(surveyMapper::map).collect(Collectors.toList());
    }

    public List<SurveyDto> save(List<SurveyDto> surveys) {
        return StreamSupport.stream(
                votingRepository.saveAll(surveys.stream().map(surveyMapper::map).collect(Collectors.toList()))
                        .spliterator(), false).map(surveyMapper::map).collect(Collectors.toList());
    }
}

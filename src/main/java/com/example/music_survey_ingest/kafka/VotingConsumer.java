package com.example.music_survey_ingest.kafka;

import com.example.music_survey_ingest.dto.SurveyDto;
import com.example.music_survey_ingest.service.VotingService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Profile("kafka")
public class VotingConsumer {

    private final VotingService votingService;

    @KafkaListener(topics = "${app.kafka.topic:music-survey}", groupId = "${app.kafka.consumer-group:music-survey-group}")
    public void listen(@Payload SurveyDto surveyDto) {
        votingService.save(List.of(surveyDto));
    }
}

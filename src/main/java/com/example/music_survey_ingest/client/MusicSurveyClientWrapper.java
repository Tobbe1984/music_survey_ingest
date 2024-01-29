package com.example.music_survey_ingest.client;

import com.example.music_survey_ingest.mapper.VotingMapper;
import com.example.music_survey_ingest.repositories.VotingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MusicSurveyClientWrapper {
    private final MusicSurveyClient musicSurveyClient;
    private final VotingRepository votingRepository;

    private final VotingMapper votingMapper = new VotingMapper();

    public void loadAll() {
        votingRepository.saveAll(musicSurveyClient.getAll().stream().map(votingMapper::map).toList());
    }
}

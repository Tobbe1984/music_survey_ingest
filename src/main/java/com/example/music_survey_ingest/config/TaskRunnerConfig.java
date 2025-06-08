package com.example.music_survey_ingest.config;

import com.example.music_survey_ingest.client.MusicSurveyClientWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@Profile("!no-task-runner")
public class TaskRunnerConfig {
    @Autowired
    MusicSurveyClientWrapper musicSurveyClientWrapper;

    @Scheduled(fixedRate=86400)
    private void loadVotingsFromMusicSurvey() {
        musicSurveyClientWrapper.loadAll();
    }
}

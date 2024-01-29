package com.example.music_survey_ingest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SurveyDto {
    private String instrument;
    private String genre;
    private Short voting;
}

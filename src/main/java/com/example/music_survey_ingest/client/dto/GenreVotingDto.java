package com.example.music_survey_ingest.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenreVotingDto {
    private InstrumentDto instrument;
    private GenreDto genre;
    private Short value;
}

package com.example.music_survey_ingest.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "music_survey")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Voting {
    @Id
    private String id;

    private String instrument;
    private String genre;
    private Short voting;
}

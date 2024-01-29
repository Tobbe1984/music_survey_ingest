package com.example.music_survey_ingest.repositories;


import com.example.music_survey_ingest.data.Voting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VotingRepository extends ElasticsearchRepository<Voting, String> {
    Page<Voting> findVotingByInstrument(String instrument, Pageable pageable);
    Page<Voting> findVotingByGenre(String genre, Pageable pageable);
}

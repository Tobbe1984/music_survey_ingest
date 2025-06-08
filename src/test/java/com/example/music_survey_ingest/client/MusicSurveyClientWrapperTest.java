package com.example.music_survey_ingest.client;

import com.example.music_survey_ingest.client.dto.GenreVotingDto;
import com.example.music_survey_ingest.client.dto.GenreVotingDtoTestHelper;
import com.example.music_survey_ingest.data.Voting;
import com.example.music_survey_ingest.data.VotingTestHelper;
import com.example.music_survey_ingest.repositories.VotingRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
class MusicSurveyClientWrapperTest {
    @Mock
    private MusicSurveyClient musicSurveyClientMock;
    @Mock
    private VotingRepository votingRepositoryMock;
    @InjectMocks
    private MusicSurveyClientWrapper musicSurveyClientWrapper;

    private final GenreVotingDto genreVotingDtoStub = GenreVotingDtoTestHelper.builder().build();
    private final Voting votingStub = VotingTestHelper.builder().build();

    @Test
    public void loadAllTest() {
        when(votingRepositoryMock.saveAll(anyList())).thenReturn(List.of(votingStub));
        when(musicSurveyClientMock.getAll()).thenReturn(List.of(genreVotingDtoStub));

        musicSurveyClientWrapper.loadAll();

        verify(musicSurveyClientMock, times(1)).getAll();
        verify(votingRepositoryMock, times(1)).saveAll(anyList());
    }
}

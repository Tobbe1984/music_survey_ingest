package com.example.music_survey_ingest.common;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;

import static java.nio.charset.Charset.defaultCharset;
import static org.springframework.util.StreamUtils.copyToString;

public class SurveyReponseMock {
    public static void setupSurveyMocksReponse(WireMockServer mockServer) throws IOException {
        mockServer.stubFor(WireMock.get(WireMock.urlEqualTo("/api/v1/voting"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(
                                copyToString(
                                        SurveyReponseMock.class.getClassLoader().getResourceAsStream("payload/music_survey.json"),
                                        defaultCharset()
                                )
                        )
                )
        );
    }
}

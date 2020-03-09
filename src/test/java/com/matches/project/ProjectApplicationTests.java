package com.matches.project;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matches.project.controller.APIController;
import com.matches.project.model.Match;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.event.annotation.BeforeTestExecution;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ProjectApplicationTests {

    MockMvc mockMvc;

    @InjectMocks
    @Autowired
    APIController apiController;

    @BeforeEach
    public void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(apiController).build();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getMatchWithValidTeam_receiveAListOfMatches() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/matches/team/chelsea")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andReturn();
        assertThat(result.getResponse().getContentAsString().split("matchId").length).isEqualTo(43);
    }

    @Test
    void getMatchWithValidTeamAndStatus_receiveAListOfMatches() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/matches/team/chelsea/status/played")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andReturn();
        assertThat(result.getResponse().getContentAsString().split("matchId").length).isEqualTo(30);
    }

    @Test
    void getMatchWithValidTeamAndStatusUpcoming_receiveAListOfMatches() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/matches/team/chelsea/status/upcoming")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andReturn();
        assertThat(result.getResponse().getContentAsString().split("matchId").length).isEqualTo(14);
    }

    @Test
    void getMatchWithInvalidTeam_receiveAnEmptyList() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/matches/team/chelseaaa/status/played")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andReturn();
        assertThat(result.getResponse().getContentAsString()).isEqualTo("[]");
    }

    @Test
    void getMatchWithInvalidStatus_receiveAnException() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/matches/team/chelsea/status/playeddd")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andReturn();
        assertThat(result.getResponse().getStatus()).isEqualTo(400);
    }

    @Test
    void getMatchWithValidTournamentName_receiveAListOfMatches() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/matches/tournament/fa")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andReturn();
        assertThat(result.getResponse().getContentAsString().split("matchId").length).isEqualTo(143);
    }

    @Test
    void getMatchWithValidTournamentNameAndStatus_receiveAListOfMatches() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/matches/tournament/fa/status/upcoming")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andReturn();
        assertThat(result.getResponse().getContentAsString().split("matchId").length).isEqualTo(5);
    }

    @Test
    void getMatchWithInvalidTournamentNameAndStatus_receiveAListOfMatches() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/matches/tournament/fa/status/comingsoon")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andReturn();
        assertThat(result.getResponse().getStatus()).isEqualTo(400);
    }
}

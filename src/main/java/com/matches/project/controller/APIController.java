package com.matches.project.controller;

import com.matches.project.model.Match;
import com.matches.project.service.ApplicationRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
public class APIController {

    @Autowired
    ApplicationRunner applicationRunner;

    @GetMapping("/matches/team/{teamName}")
    public List<Match> getMatchesByTeamName(@PathVariable String teamName){
        List<Match> list = StreamSupport.stream(applicationRunner.getPlayedMatchesRepo().findAll().spliterator(), false)
                .filter(match -> match.getHomeTeam().getName().equalsIgnoreCase(teamName) || match.getAwayTeam().getName().equalsIgnoreCase(teamName)).collect(Collectors.toList());
        list.addAll(StreamSupport.stream(applicationRunner.getUpcomingMatchesRepo().findAll().spliterator(), false)
                .filter(match -> match.getHomeTeam().getName().equalsIgnoreCase(teamName) || match.getAwayTeam().getName().equalsIgnoreCase(teamName)).collect(Collectors.toList()));

        return list;
    }

    @GetMapping("/matches/team/{teamName}/status/{status}")
    public List<Match> getMatchesByTeamNameAndStatus(@PathVariable String teamName, @PathVariable String status){
        List<Match> list;
        if (status.equalsIgnoreCase(Match.Status.PLAYED.name())){
            list = StreamSupport.stream(applicationRunner.getPlayedMatchesRepo().findAll().spliterator(), false)
                    .filter(match -> match.getHomeTeam().getName().equalsIgnoreCase(teamName) || match.getAwayTeam().getName().equalsIgnoreCase(teamName)).collect(Collectors.toList());
        }
        else if(status.equalsIgnoreCase(Match.Status.UPCOMING.name())) {
            list = StreamSupport.stream(applicationRunner.getUpcomingMatchesRepo().findAll().spliterator(), false)
                    .filter(match -> match.getHomeTeam().getName().equalsIgnoreCase(teamName) || match.getAwayTeam().getName().equalsIgnoreCase(teamName)).collect(Collectors.toList());
        }
        else {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "status: "+status+" does not exist, please choose one of the following: played,upcoming");
        }

        return list;
    }

    @GetMapping("/matches/tournament/{tournamentName}")
    public List<Match> getMatchesByTournamentName(@PathVariable String tournamentName){
        List<Match> list = StreamSupport.stream(applicationRunner.getPlayedMatchesRepo().findAll().spliterator(), false)
                .filter(match -> match.getTournament().getName().equalsIgnoreCase(tournamentName)).collect(Collectors.toList());
        list.addAll(StreamSupport.stream(applicationRunner.getUpcomingMatchesRepo().findAll().spliterator(), false)
                .filter(match -> match.getTournament().getName().equalsIgnoreCase(tournamentName)).collect(Collectors.toList()));

        return list;
    }

    @GetMapping("/matches/tournament/{tournamentName}/status/{status}")
    public List<Match> getMatchesByTournamentNameAndStatus(@PathVariable String tournamentName, @PathVariable String status){
        List<Match> list;
        if (status.equalsIgnoreCase(Match.Status.PLAYED.name())){
            list = StreamSupport.stream(applicationRunner.getPlayedMatchesRepo().findAll().spliterator(), false)
                    .filter(match -> match.getTournament().getName().equalsIgnoreCase(tournamentName)).collect(Collectors.toList());
        }
        else if(status.equalsIgnoreCase(Match.Status.UPCOMING.name())) {
            list = StreamSupport.stream(applicationRunner.getUpcomingMatchesRepo().findAll().spliterator(), false)
                    .filter(match -> match.getTournament().getName().equalsIgnoreCase(tournamentName)).collect(Collectors.toList());
        }
        else {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "status: "+status+" does not exist, please choose one of the following: played,upcoming");
        }

        return list;
    }
}

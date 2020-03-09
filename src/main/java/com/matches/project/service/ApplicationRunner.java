package com.matches.project.service;

import ch.qos.logback.core.CoreConstants;
import com.matches.project.model.PlayedMatch;
import com.matches.project.model.Team;
import com.matches.project.model.Tournament;
import com.matches.project.model.UpcomingMatch;
import com.matches.project.repository.PlayedMatchesRepo;
import com.matches.project.repository.TeamRepo;
import com.matches.project.repository.TournamentRepo;
import com.matches.project.repository.UpcomingMatchesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class ApplicationRunner {

    static final String PLAYED_MATCHES_FILE_PATH = "result_played.csv";
    static final String UPCOMING_MATCHES_FILE_PATH = "result_upcoming.csv";

    @Autowired
    PlayedMatchesRepo playedMatchesRepo;
    @Autowired
    UpcomingMatchesRepo upcomingMatchesRepo;
    @Autowired
    TeamRepo teamRepo;
    @Autowired
    TournamentRepo tournamentRepo;

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            //load played matches from file.
            URL url =  getClass().getClassLoader().getResource(PLAYED_MATCHES_FILE_PATH);
            File file =  Paths.get(url.toURI()).toFile();

            List<List<String>> records = ParserService.parseFile(file.getAbsolutePath());
            //remove headlines row
            records.remove(0);
            //populate playedMatches repository with played matches from file.
            for (List<String> record: records){
                PlayedMatch playedMatch = new PlayedMatch();
                //if team is already in repo, fetch it from there.
                playedMatch.setHomeTeam(getOrCreateTeams(record.get(0)));
                playedMatch.setHomeScore(Integer.parseInt(record.get(1)));
                playedMatch.setAwayTeam(getOrCreateTeams(record.get(2)));
                playedMatch.setAwayScore(Integer.parseInt(record.get(3)));
                playedMatch.setTournament(getOrCreateTournament(record.get(4)));
                playedMatch.setStartDate(record.get(5));
                playedMatchesRepo.save(playedMatch);
            }

            //load upcoming matches file.
            url =  getClass().getClassLoader().getResource(UPCOMING_MATCHES_FILE_PATH);
            file =  Paths.get(url.toURI()).toFile();

            records = ParserService.parseFile(file.getAbsolutePath());

            //remove headlines row
            records.remove(0);

            //populate upcomingMatches repository with upcoming matches from file.
            for (List<String> record: records){
                UpcomingMatch upcomingMatch = new UpcomingMatch();
                upcomingMatch.setHomeTeam(getOrCreateTeams(record.get(0)));
                upcomingMatch.setAwayTeam(getOrCreateTeams(record.get(1)));
                upcomingMatch.setTournament(getOrCreateTournament(record.get(2)));
                upcomingMatch.setStartDate(record.get(3));
                String[] time = record.get(4).split(":");
                upcomingMatch.setTime(LocalTime.of(Integer.parseInt(time[0]),Integer.parseInt(time[1])));
                upcomingMatchesRepo.save(upcomingMatch);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {

        }
    }

    //get team from repo if exists, otherwise create a new one with the given name.
    private Team getOrCreateTeams(String teamName) {
        Optional<Team> optionalTeam = StreamSupport.stream(teamRepo.findAll().spliterator(), false)
                .filter(team -> team.getName().equals(teamName)).findFirst();
        if (optionalTeam.isPresent()){
            return optionalTeam.get();
        }
        else {
            Team team = new Team(teamName);
            teamRepo.save(team);
            return team;
        }
    }

    private Tournament getOrCreateTournament(String tournamentName) {
        Optional<Tournament> optionalTournament = StreamSupport.stream(tournamentRepo.findAll().spliterator(), false)
                .filter(tournament -> tournament.getName().equals(tournamentName)).findFirst();
        if (optionalTournament.isPresent()) {
            return optionalTournament.get();
        } else {
            Tournament tournament = new Tournament(tournamentName);
            tournamentRepo.save(tournament);
            return tournament;
        }
    }

    public PlayedMatchesRepo getPlayedMatchesRepo() {
        return playedMatchesRepo;
    }

    public void setPlayedMatchesRepo(PlayedMatchesRepo playedMatchesRepo) {
        this.playedMatchesRepo = playedMatchesRepo;
    }

    public UpcomingMatchesRepo getUpcomingMatchesRepo() {
        return upcomingMatchesRepo;
    }

    public void setUpcomingMatchesRepo(UpcomingMatchesRepo upcomingMatchesRepo) {
        this.upcomingMatchesRepo = upcomingMatchesRepo;
    }

    public TeamRepo getTeamRepo() {
        return teamRepo;
    }

    public void setTeamRepo(TeamRepo teamRepo) {
        this.teamRepo = teamRepo;
    }

    public TournamentRepo getTournamentRepo() {
        return tournamentRepo;
    }

    public void setTournamentRepo(TournamentRepo tournamentRepo) {
        this.tournamentRepo = tournamentRepo;
    }
}


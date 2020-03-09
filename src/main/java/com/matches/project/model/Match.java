package com.matches.project.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public abstract class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    long matchId;

    @OneToOne(cascade = CascadeType.MERGE)
    private Team homeTeam;
    @OneToOne(cascade = CascadeType.MERGE)
    private Team awayTeam;
    private String startDate;
    @OneToOne(cascade = CascadeType.MERGE)
    private Tournament tournament;

    public enum Status {
        PLAYED, UPCOMING
    }

    Status status;


    public long getMatchId() {
        return matchId;
    }

    public void setMatchId(long matchId) {
        this.matchId = matchId;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(Team awayTeam) {
        this.awayTeam = awayTeam;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Match match = (Match) o;
        return matchId == match.matchId &&
                Objects.equals(homeTeam, match.homeTeam) &&
                Objects.equals(awayTeam, match.awayTeam) &&
                Objects.equals(startDate, match.startDate) &&
                Objects.equals(tournament, match.tournament) &&
                status == match.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(matchId, homeTeam, awayTeam, startDate, tournament, status);
    }
}

package com.matches.project.model;

import javax.persistence.Entity;

@Entity
public class PlayedMatch extends Match {
    int homeScore;
    int awayScore;

    public PlayedMatch() {
        status = Status.PLAYED;
    }

    public int getHomeScore() {
        return homeScore;
    }

    public void setHomeScore(int homeScore) {
        this.homeScore = homeScore;
    }

    public int getAwayScore() {
        return awayScore;
    }

    public void setAwayScore(int awayScore) {
        this.awayScore = awayScore;
    }
}

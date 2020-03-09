package com.matches.project.repository;

import com.matches.project.model.Tournament;
import org.springframework.data.repository.CrudRepository;

public interface TournamentRepo  extends CrudRepository<Tournament,Integer> {
}

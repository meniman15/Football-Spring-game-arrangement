package com.matches.project.repository;

import org.springframework.data.repository.CrudRepository;
import com.matches.project.model.PlayedMatch;

public interface PlayedMatchesRepo extends CrudRepository<PlayedMatch,Integer> {

}

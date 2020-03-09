package com.matches.project.repository;

import com.matches.project.model.Team;
import org.springframework.data.repository.CrudRepository;

public interface TeamRepo extends CrudRepository<Team,Integer> {
}

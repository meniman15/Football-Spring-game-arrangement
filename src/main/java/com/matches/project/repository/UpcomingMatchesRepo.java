package com.matches.project.repository;

import com.matches.project.model.UpcomingMatch;
import org.springframework.data.repository.CrudRepository;

public interface UpcomingMatchesRepo  extends CrudRepository<UpcomingMatch,Integer> {
}

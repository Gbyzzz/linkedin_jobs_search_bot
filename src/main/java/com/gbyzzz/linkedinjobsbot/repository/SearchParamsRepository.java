package com.gbyzzz.linkedinjobsbot.repository;

import com.gbyzzz.linkedinjobsbot.entity.SearchParams;
import com.gbyzzz.linkedinjobsbot.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SearchParamsRepository extends JpaRepository<SearchParams, Long> {
}

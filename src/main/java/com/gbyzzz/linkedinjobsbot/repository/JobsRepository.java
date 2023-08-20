package com.gbyzzz.linkedinjobsbot.repository;

import com.gbyzzz.linkedinjobsbot.entity.Job;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobsRepository extends MongoRepository<Job, Long> {
    @Query("{ " +
            "'description': { $regex: ?0, $options: 'i' }," +
            "'name': { $regex: ?1, $options: 'i' }" +
            "}")
    List<Job> findJobsIncludingAndExcludingWords(String includeRegex, String excludeRegex);

}


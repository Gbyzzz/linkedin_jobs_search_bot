package com.gbyzzz.linkedinjobsbot.modules.mongodb.repository;

import com.gbyzzz.linkedinjobsbot.modules.mongodb.entity.Job;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobsRepository extends MongoRepository<Job, Long> {
    @Query("{ '$and': [" +
            "    { 'description': { $regex: ?0, $options: 'i' } }," +
            "    { 'name': { $regex: ?1, $options: 'i' } }," +
            "    { 'searchParamsId': ?2 }" +
            "  ] }")
//    @Query("{ " +
//            "'description': { $regex: ?0, $options: 'i' }," +
//            "'name': { $regex: ?1, $options: 'i' }," +
//            "'searchParamsId': ?2" +
//            "}")
    List<Job> findJobsIncludingAndExcludingWords(String includeRegex, String excludeRegex, Long searchParamsId);

    Optional<Job> findJobById(Long id);
}


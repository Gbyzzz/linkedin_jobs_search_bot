package com.gbyzzz.linkedinjobsbot.modules.mongodb.repository;

import com.gbyzzz.linkedinjobsbot.modules.mongodb.entity.CV;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CVRepository extends MongoRepository<CV, ObjectId> {
    List<CV> getCVByUserId(Long userId);
}

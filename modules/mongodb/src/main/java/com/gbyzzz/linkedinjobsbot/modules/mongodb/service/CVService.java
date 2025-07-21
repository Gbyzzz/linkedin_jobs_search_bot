package com.gbyzzz.linkedinjobsbot.modules.mongodb.service;

import com.gbyzzz.linkedinjobsbot.modules.mongodb.entity.CV;
import org.bson.types.ObjectId;

import java.util.List;

public interface CVService {
    CV getCVById(ObjectId id);
    List<CV> getCVByUserId(Long userId);
    void saveCV(CV cv);
}

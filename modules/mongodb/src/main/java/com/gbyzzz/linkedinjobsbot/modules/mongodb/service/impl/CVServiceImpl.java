package com.gbyzzz.linkedinjobsbot.modules.mongodb.service.impl;

import com.gbyzzz.linkedinjobsbot.modules.mongodb.entity.CV;
import com.gbyzzz.linkedinjobsbot.modules.mongodb.repository.CVRepository;
import com.gbyzzz.linkedinjobsbot.modules.mongodb.repository.JobsRepository;
import com.gbyzzz.linkedinjobsbot.modules.mongodb.service.CVService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CVServiceImpl implements CVService {

    private final CVRepository cvRepository;

    @Override
    public CV getCVById(ObjectId id) {
        return cvRepository.findById(id).orElse(null);
    }

    @Override
    public List<CV> getCVByUserId(Long userId) {
        return cvRepository.getCVByUserId(userId);
    }

    @Override
    public void saveCV(CV cv) {
        cvRepository.save(cv);
    }
}

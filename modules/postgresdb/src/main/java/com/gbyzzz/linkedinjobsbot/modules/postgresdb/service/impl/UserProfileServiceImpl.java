package com.gbyzzz.linkedinjobsbot.modules.postgresdb.service.impl;


import com.gbyzzz.linkedinjobsbot.modules.postgresdb.entity.UserProfile;
import com.gbyzzz.linkedinjobsbot.modules.postgresdb.repository.UserProfileRepository;
import com.gbyzzz.linkedinjobsbot.modules.postgresdb.service.UserProfileService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepository userProfileRepository;

    public UserProfileServiceImpl(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    @Override
    public void save(UserProfile userProfile) {
        userProfileRepository.save(userProfile);
    }

    @Override
    public UserProfile getUserProfileById(Long chatId) {
        return userProfileRepository.findById(chatId).orElseThrow(() ->
                new NoSuchElementException("No User Profile found with id " + chatId));
    }

    @Override
    public boolean userProfileExistsByChatId(Long chatId) {
        return userProfileRepository.existsById(chatId);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        userProfileRepository.delete(userProfileRepository.getReferenceById(id));
    }

    @Override
    public List<UserProfile> getAll() {
        return userProfileRepository.findAll();
    }

    @Override
    public UserProfile getUserProfileByUsername(String username) {
        return userProfileRepository.findByUsername(username).orElseThrow();
    }
}

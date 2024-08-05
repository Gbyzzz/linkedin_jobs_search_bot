package com.gbyzzz.linkedinjobsbot.service.impl;

import com.gbyzzz.linkedinjobsbot.entity.UserProfile;
import com.gbyzzz.linkedinjobsbot.repository.UserProfileRepository;
import com.gbyzzz.linkedinjobsbot.service.UserProfileService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public Optional<UserProfile> getUserProfileById(Long chatId) {
        return userProfileRepository.findById(chatId);
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
}

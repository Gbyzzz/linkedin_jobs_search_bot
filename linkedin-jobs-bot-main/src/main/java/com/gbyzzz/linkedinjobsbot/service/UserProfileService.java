package com.gbyzzz.linkedinjobsbot.service;

import com.gbyzzz.linkedinjobsbot.entity.UserProfile;

import java.util.List;
import java.util.Optional;

public interface UserProfileService {
    void save(UserProfile userProfile);

    Optional<UserProfile> getUserProfileById(Long chatId);

    boolean userProfileExistsByChatId(Long chatId);

    void delete(Long id);
    List<UserProfile> getAll();
}

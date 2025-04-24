package com.gbyzzz.linkedinjobsbotentityservice.service;

import com.gbyzzz.linkedinjobsbotentityservice.entity.UserProfile;

import java.util.List;

public interface UserProfileService {
    void save(UserProfile userProfile);

    UserProfile getUserProfileById(Long chatId);

    boolean userProfileExistsByChatId(Long chatId);

    void delete(Long id);
    List<UserProfile> getAll();
}

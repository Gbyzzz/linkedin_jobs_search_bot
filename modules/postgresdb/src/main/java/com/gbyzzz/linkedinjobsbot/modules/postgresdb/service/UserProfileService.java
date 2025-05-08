package com.gbyzzz.linkedinjobsbot.modules.postgresdb.service;


import com.gbyzzz.linkedinjobsbot.modules.postgresdb.entity.UserProfile;

import java.util.List;

public interface UserProfileService {
    void save(UserProfile userProfile);

    UserProfile getUserProfileById(Long chatId);

    boolean userProfileExistsByChatId(Long chatId);

    void delete(Long id);
    List<UserProfile> getAll();
}

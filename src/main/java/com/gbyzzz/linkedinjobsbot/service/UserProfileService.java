package com.gbyzzz.linkedinjobsbot.service;

import com.gbyzzz.linkedinjobsbot.entity.UserProfile;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Chat;

import java.util.List;
import java.util.Optional;

public interface UserProfileService {
    void save(UserProfile userProfile);

    Optional<UserProfile> getUserProfileById(Long chatId);

    boolean userProfileExistsByChatId(Long chatId);

    void delete(Long id);
    List<UserProfile> getAll();
}

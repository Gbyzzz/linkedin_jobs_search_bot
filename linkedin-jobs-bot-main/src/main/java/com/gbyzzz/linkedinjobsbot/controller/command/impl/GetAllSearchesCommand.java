package com.gbyzzz.linkedinjobsbot.controller.command.impl;

import com.gbyzzz.linkedinjobsbot.controller.command.Command;
import com.gbyzzz.linkedinjobsbot.dto.Reply;
import com.gbyzzz.linkedinjobsbot.modules.commons.values.MessageText;
import com.gbyzzz.linkedinjobsbot.modules.postgresdb.entity.UserProfile;
import com.gbyzzz.linkedinjobsbot.modules.postgresdb.service.UserProfileService;
import com.gbyzzz.linkedinjobsbot.service.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;

@Component(MessageText.GET_ALL_SEARCHES)
@AllArgsConstructor
public class GetAllSearchesCommand implements Command {

    private final UserProfileService userProfileService;
    private final MessageService messageService;

    @Override
    public Reply execute(Update update) throws IOException {
        UserProfile userProfile = userProfileService.getUserProfileById(update.getMessage().getChatId());
        userProfile.setBotState(UserProfile.BotState.SEARCHES);
        userProfileService.save(userProfile);
        return new Reply(messageService.getSearchByUserId(update.getMessage().getChatId(),
                new String[]{MessageText.SEARCHES, MessageText.FIRST}), false);
    }
}

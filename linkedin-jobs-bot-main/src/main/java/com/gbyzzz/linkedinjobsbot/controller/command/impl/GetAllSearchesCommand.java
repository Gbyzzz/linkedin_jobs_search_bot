package com.gbyzzz.linkedinjobsbot.controller.command.impl;

import com.gbyzzz.linkedinjobsbot.controller.MessageText;
import com.gbyzzz.linkedinjobsbot.controller.command.Command;
import com.gbyzzz.linkedinjobsbot.controller.command.keyboard.PaginationKeyboard;
import com.gbyzzz.linkedinjobsbot.dto.Reply;
import com.gbyzzz.linkedinjobsbot.entity.SearchParams;
import com.gbyzzz.linkedinjobsbot.entity.UserProfile;
import com.gbyzzz.linkedinjobsbot.service.MessageService;
import com.gbyzzz.linkedinjobsbot.service.SearchParamsService;
import com.gbyzzz.linkedinjobsbot.service.UserProfileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.util.List;

@Component(MessageText.GET_ALL_SEARCHES)
@AllArgsConstructor
public class GetAllSearchesCommand implements Command {

    private final UserProfileService userProfileService;
    private final MessageService messageService;

    @Override
    public Reply execute(Update update) throws IOException {
        UserProfile userProfile = userProfileService.getUserProfileById(update.getMessage().getChatId()).get();
        userProfile.setBotState(UserProfile.BotState.SEARCHES);
        userProfileService.save(userProfile);
        return new Reply(messageService.getSearchByUserId(update.getMessage().getChatId(),
                new String[]{MessageText.SEARCHES, MessageText.FIRST}), false);
    }
}

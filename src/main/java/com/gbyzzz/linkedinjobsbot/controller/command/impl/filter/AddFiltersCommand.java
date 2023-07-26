package com.gbyzzz.linkedinjobsbot.controller.command.impl.filter;

import com.gbyzzz.linkedinjobsbot.controller.command.Command;
import com.gbyzzz.linkedinjobsbot.entity.UserProfile;
import com.gbyzzz.linkedinjobsbot.service.UserProfileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component("ADD_FILTERS")
@AllArgsConstructor
public class AddFiltersCommand implements Command {

    private final UserProfileService userProfileService;

    @Override
    public SendMessage execute(Update update) {
        UserProfile userProfile = userProfileService.getUserProfileById(update.getCallbackQuery().getMessage().getChatId()).get();
        userProfile.setBotState(UserProfile.BotState.ADD_FILTER_INCLUDE);
        userProfileService.save(userProfile);
        String reply  = "Enter keywords that should be included(separate them by space)";
        return new SendMessage(update.getMessage().getChatId().toString(), reply);
    }
}

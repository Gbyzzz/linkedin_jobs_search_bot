package com.gbyzzz.linkedinjobsbot.controller.command.impl.search;

import com.gbyzzz.linkedinjobsbot.controller.MessageText;
import com.gbyzzz.linkedinjobsbot.controller.command.Command;
import com.gbyzzz.linkedinjobsbot.dto.Reply;
import com.gbyzzz.linkedinjobsbot.entity.UserProfile;
import com.gbyzzz.linkedinjobsbot.service.UserProfileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component("ADD_SEARCH")
@AllArgsConstructor
public class AddSearchCommand implements Command {

    private final UserProfileService userProfileService;

    @Override
    public Reply execute(Update update) {
        UserProfile userProfile = userProfileService.getUserProfileById(update.getMessage()
                .getChatId()).get();
        userProfile.setBotState(UserProfile.BotState.ADD_KEYWORDS);
        userProfileService.save(userProfile);
        return new Reply(new SendMessage(update.getMessage().getChatId().toString(),
                MessageText.ADD_SEARCH_REPLY.getValue()),false);
    }
}

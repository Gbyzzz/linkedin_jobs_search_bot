package com.gbyzzz.linkedinjobsbot.controller.command.impl;

import com.gbyzzz.linkedinjobsbot.controller.command.Command;
import com.gbyzzz.linkedinjobsbot.dto.Reply;
import com.gbyzzz.linkedinjobsbot.modules.commons.values.MessageText;
import com.gbyzzz.linkedinjobsbot.modules.postgresdb.service.UserProfileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component("EXIT")
@AllArgsConstructor
public class ExitCommand implements Command {
    private final UserProfileService userProfileService;
    @Override
    public Reply execute(Update update) {
        userProfileService.delete(update.getMessage().getChat().getId());
        return new Reply(new SendMessage(update.getMessage().getChatId().toString(),
                MessageText.EXIT),false);
    }
}

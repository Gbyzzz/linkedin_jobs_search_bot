package com.gbyzzz.linkedinjobsbot.controller.command.impl;

import com.gbyzzz.linkedinjobsbot.controller.command.Command;
import com.gbyzzz.linkedinjobsbot.controller.command.keyboard.MainMenuKeyboard;
import com.gbyzzz.linkedinjobsbot.dto.Reply;
import com.gbyzzz.linkedinjobsbot.entity.UserProfile;
import com.gbyzzz.linkedinjobsbot.service.UserProfileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Date;

@Component("START")
@AllArgsConstructor
public class StartCommand implements Command {

    private final UserProfileService userProfileService;
    private final MainMenuKeyboard mainMenuKeyboard;

    private static final String REPLY = "\uD83D\uDE80 Starting \uD83D\uDE80 \n " +
            "Input /add_search to add search to you account";

    private static final String REPLY_ALREADY_STARTED = "\uD83D\uDE80 Starting \uD83D\uDE80 \n " +
            "Input /add_search to add search to you account";

    @Override
    public Reply execute(Update update) {
        SendMessage sendMessage;
        if (!userProfileService.userProfileExistsByChatId(update.getMessage().getChatId())) {
            String username = update.getMessage().getFrom().getUserName() != null ?
            update.getMessage().getFrom().getUserName() :
                    update.getMessage().getFrom().getFirstName();
            userProfileService.save(new UserProfile(update.getMessage().getChatId(),
                    username,
                    UserProfile.BotState.NA, new Date(System.currentTimeMillis())));
            sendMessage = new SendMessage(update.getMessage().getChatId().toString(), REPLY);
            sendMessage.setReplyMarkup(mainMenuKeyboard.getReplyButtons());
        } else {
            sendMessage = new SendMessage(update.getMessage().getChatId().toString(), REPLY_ALREADY_STARTED);
            sendMessage.setReplyMarkup(mainMenuKeyboard.getReplyButtons());
        }
        return new Reply(sendMessage, false);
    }
}

package com.gbyzzz.linkedinjobsbot.controller.command.impl;

import com.gbyzzz.linkedinjobsbot.controller.command.Command;
import com.gbyzzz.linkedinjobsbot.controller.command.keyboard.MainMenuKeyboard;
import com.gbyzzz.linkedinjobsbot.entity.UserProfile;
import com.gbyzzz.linkedinjobsbot.service.UserProfileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component("START")
@AllArgsConstructor
public class StartCommand implements Command {

    private final UserProfileService userProfileService;
    private final MainMenuKeyboard mainMenuKeyboard;

    @Override
    public SendMessage execute(Update update) {
        if (!userProfileService.userProfileExistsByChatId(update.getMessage().getChatId())) {
            userProfileService.save(new UserProfile(update.getMessage().getChatId(),
                    update.getMessage().getFrom().getUserName(),
                    UserProfile.BotState.NA, null));
            String reply = "\uD83D\uDE80 Starting \uD83D\uDE80 \n Input /add_search to add search to you account";
            SendMessage sendMessage = new SendMessage(update.getMessage().getChatId().toString(), reply);
            sendMessage.setReplyMarkup(mainMenuKeyboard.getReplyButtons());
            return sendMessage;
        } else {
            String reply = "You have already started the bot";
            SendMessage sendMessage = new SendMessage(update.getMessage().getChatId().toString(), reply);
            sendMessage.setReplyMarkup(mainMenuKeyboard.getReplyButtons());
            return sendMessage;
        }
    }
}

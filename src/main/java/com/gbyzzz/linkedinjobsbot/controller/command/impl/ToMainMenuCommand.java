package com.gbyzzz.linkedinjobsbot.controller.command.impl;

import com.gbyzzz.linkedinjobsbot.controller.MessageText;
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

@Component(MessageText.MAIN_MENU)
@AllArgsConstructor
public class ToMainMenuCommand implements Command {

    private final UserProfileService userProfileService;
    private final MainMenuKeyboard mainMenuKeyboard;

    @Override
    public Reply execute(Update update) {
        UserProfile userProfile = userProfileService.getUserProfileById(update.getMessage().getChatId()).get();
        userProfile.setBotState(UserProfile.BotState.ADD_KEYWORDS);
        userProfileService.save(userProfile);
        SendMessage sendMessage = new SendMessage(update.getMessage().getChatId().toString(),
                MessageText.MAIN_MENU_REPLY);
        sendMessage.setReplyMarkup(mainMenuKeyboard.getReplyButtons());
        return new Reply(sendMessage, false);

    }
}

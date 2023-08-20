package com.gbyzzz.linkedinjobsbot.controller;

import com.gbyzzz.linkedinjobsbot.controller.command.Command;
import com.gbyzzz.linkedinjobsbot.entity.UserProfile;
import com.gbyzzz.linkedinjobsbot.service.UserProfileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;

@Component
@AllArgsConstructor
final class CommandProvider {

    private final Map<String, Command> repository;
    private final UserProfileService userProfileService;

    Command getCommand(Update update) {
        Long id;
        CommandName commandName = null;
        if (update.hasCallbackQuery()) {
            id = update.getCallbackQuery().getMessage().getChatId();
            commandName = CommandName.getValue(userProfileService.getUserProfileById(id)
                    .map(UserProfile::getBotState).orElse(UserProfile.BotState.NA).name());
        } else {
            id = update.getMessage().getChatId();
            UserProfile.BotState botState = userProfileService.getUserProfileById(id)
                    .map(UserProfile::getBotState).orElse(UserProfile.BotState.NA);
            if (update.getMessage().getText().charAt(0) == '/') {
                commandName = CommandName.getValue(update.getMessage().getText());
            } else {
                commandName = CommandName.getValue(botState.name());
            }
        }
        return repository.getOrDefault(commandName.name(), repository.get(MessageText.WRONG));
    }
}

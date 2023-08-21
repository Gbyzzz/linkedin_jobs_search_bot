package com.gbyzzz.linkedinjobsbot.controller;

import com.gbyzzz.linkedinjobsbot.controller.command.Command;
import com.gbyzzz.linkedinjobsbot.entity.UserProfile;
import com.gbyzzz.linkedinjobsbot.service.UserProfileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;
import java.util.Optional;

@Component
@AllArgsConstructor
final class CommandProvider {

    private final Map<String, Command> repository;
    private final UserProfileService userProfileService;

    Command getCommand(Update update) {
        Long id = update.hasCallbackQuery() ?
                update.getCallbackQuery().getMessage().getChatId() :
                update.getMessage().getChatId();
        CommandName commandName = null;
        Optional<UserProfile> userProfile = userProfileService.getUserProfileById(id);
        if (userProfile.isEmpty() && !update.getMessage().getText()
                .equals(MessageText.BUTTON_START)) {
            commandName = CommandName.getValue(MessageText.NO_ACCOUNT);
        } else {
            if (update.hasCallbackQuery()) {
                commandName = CommandName.getValue(userProfile.get().getBotState().name());
            } else {
                UserProfile.BotState botState = userProfile.map(UserProfile::getBotState)
                        .orElse(UserProfile.BotState.NA);
                if (update.getMessage().getText().charAt(0) == MessageText.SLASH.charAt(0)) {
                    commandName = CommandName.getValue(update.getMessage().getText());
                } else {
                    commandName = CommandName.getValue(botState.name());
                }
            }
        }
        return repository.getOrDefault(commandName.name(), repository.get(MessageText.WRONG));
    }
}

package com.gbyzzz.linkedinjobsbot.controller;

import com.gbyzzz.linkedinjobsbot.controller.command.Command;
import com.gbyzzz.linkedinjobsbot.modules.commons.values.MessageText;
import com.gbyzzz.linkedinjobsbot.modules.postgresdb.entity.UserProfile;
import com.gbyzzz.linkedinjobsbot.modules.postgresdb.service.UserProfileService;
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
        Long id = update.hasCallbackQuery() ?
                update.getCallbackQuery().getMessage().getChatId() :
                update.getMessage().getChatId();

        CommandName commandName;

        boolean isUserExist = userProfileService.userProfileExistsByChatId(id);

        if (!isUserExist && !update.getMessage().getText().equals(MessageText.BUTTON_START)) {
            commandName = CommandName.getValue(MessageText.NO_ACCOUNT);
        } else {
            if (isUserExist && update.hasCallbackQuery()) {
                if(update.getCallbackQuery().getData().chars().filter(ch -> ch == '_').count() == 4){
                    commandName = CommandName.getValue(UserProfile.BotState.NEW.name());
                } else {
                    UserProfile userProfile = userProfileService.getUserProfileById(id);
                    commandName = CommandName.getValue(userProfile.getBotState().name());
                }
            } else {
                UserProfile.BotState botState = isUserExist && userProfileService.getUserProfileById(id).getBotState() != null ?
                        userProfileService.getUserProfileById(id).getBotState() : UserProfile.BotState.NA;
                if (update.hasMessage() && update.getMessage().getText().charAt(0) == MessageText.SLASH.charAt(0)) {
                    commandName = CommandName.getValue(update.getMessage().getText());
                } else {
                    commandName = CommandName.getValue(botState.name());
                }
            }
        }
        return repository.getOrDefault(commandName.name(), repository.get(MessageText.WRONG));
    }
}

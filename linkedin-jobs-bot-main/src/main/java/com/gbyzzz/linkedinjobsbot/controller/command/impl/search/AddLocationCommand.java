package com.gbyzzz.linkedinjobsbot.controller.command.impl.search;

import com.gbyzzz.linkedinjobsbot.controller.command.Command;
import com.gbyzzz.linkedinjobsbot.controller.command.keyboard.ExperienceKeyboard;
import com.gbyzzz.linkedinjobsbot.dto.Reply;
import com.gbyzzz.linkedinjobsbot.modules.commons.values.MessageText;
import com.gbyzzz.linkedinjobsbot.modules.dto.dto.SearchParamsDTO;
import com.gbyzzz.linkedinjobsbot.modules.postgresdb.entity.UserProfile;
import com.gbyzzz.linkedinjobsbot.modules.postgresdb.service.UserProfileService;
import com.gbyzzz.linkedinjobsbot.modules.redisdb.service.RedisService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.gbyzzz.linkedinjobsbot.controller.command.keyboard.ExperienceKeyboard.putExperienceValue;
import static com.gbyzzz.linkedinjobsbot.controller.command.keyboard.ExperienceKeyboard.setExperienceKeyboardState;

@Component(MessageText.ADD_LOCATION)
@AllArgsConstructor
public class AddLocationCommand implements Command {

    private final UserProfileService userProfileService;
    private final ExperienceKeyboard experienceKeyboard;
    private final RedisService redisService;

    @Override
    public Reply execute(Update update) {
        Long id = update.getCallbackQuery().getMessage().getChatId();
        SendMessage sendMessage;
        SearchParamsDTO searchParams = redisService.getFromTempRepository(id);
        redisService.saveToTempRepository(new SearchParamsDTO(searchParams.id(), searchParams.keywords(),
                update.getCallbackQuery().getData(), searchParams.searchFilters(),
                searchParams.filterParams()), id);
        UserProfile userProfile = userProfileService
                .getUserProfileById(id);
        userProfile.setBotState(UserProfile.BotState.ADD_EXPERIENCE);
        userProfileService.save(userProfile);
        setExperienceKeyboardState(false);
        if (searchParams.searchFilters().get(MessageText.EXPERIENCE) != null) {
            putExperienceValue(searchParams.searchFilters().get(MessageText.EXPERIENCE));
        }
        StringBuilder stringBuilder = new StringBuilder();
        if(searchParams.id() != null) {
            stringBuilder.append(MessageText.CANCEL_EDITING_COMMAND)
                    .append(MessageText.ADD_LOCATION_REPLY);
        } else {
            stringBuilder.append(MessageText.ADD_LOCATION_REPLY);
        }
        sendMessage = new SendMessage(id.toString(), stringBuilder.toString());
        sendMessage.setReplyMarkup(experienceKeyboard.getReplyButtons());
        return new Reply(sendMessage, false);
    }
}

package com.gbyzzz.linkedinjobsbot.controller.command.impl.search;

import com.gbyzzz.linkedinjobsbot.controller.command.Command;
import com.gbyzzz.linkedinjobsbot.controller.command.keyboard.LocationKeyboard;
import com.gbyzzz.linkedinjobsbot.dto.Reply;
import com.gbyzzz.linkedinjobsbot.modules.commons.values.MessageText;
import com.gbyzzz.linkedinjobsbot.modules.dto.dto.FilterParamsDTO;
import com.gbyzzz.linkedinjobsbot.modules.dto.dto.SearchParamsDTO;
import com.gbyzzz.linkedinjobsbot.modules.postgresdb.entity.UserProfile;
import com.gbyzzz.linkedinjobsbot.modules.postgresdb.service.UserProfileService;
import com.gbyzzz.linkedinjobsbot.modules.redisdb.service.RedisService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;

@Component(MessageText.ADD_KEYWORDS)
@AllArgsConstructor
public class AddKeywordsCommand implements Command {

    private final UserProfileService userProfileService;
    private final RedisService redisService;
    private final LocationKeyboard locationKeyboard;

    @Override
    public Reply execute(Update update) {
        Long id = update.getMessage().getChatId();
        UserProfile userProfile = userProfileService.getUserProfileById(id);
        SearchParamsDTO searchParams = redisService.getFromTempRepository(id);
        if (searchParams == null) {
            searchParams = new SearchParamsDTO(null, null, null, new HashMap<>(), new FilterParamsDTO());
        }
        String[] keywords = searchParams.keywords();
        if (!update.getMessage().getText().equals(MessageText.PLUS)) {
            keywords = update.getMessage().getText().split(MessageText.SPACE);
            redisService.saveToTempRepository(new SearchParamsDTO(searchParams.id(), keywords,
                    searchParams.location(), searchParams.searchFilters(),
                    searchParams.filterParams()), update.getMessage().getChatId());
        }
            userProfile.setBotState(UserProfile.BotState.ADD_LOCATION);
            userProfileService.save(userProfile);
        StringBuilder reply = new StringBuilder(MessageText.INPUTTED_KEYWORDS);
        reply.append(MessageText.NEW_LINE);
        for (String word : keywords) {
            reply.append(word).append(MessageText.NEW_LINE);
        }
        if (searchParams.location() != null) {
            reply.append(MessageText.NEW_LINE).append(MessageText.LOCATION_EDIT)
                    .append(searchParams.location()).append(MessageText.NEW_LINE)
                    .append(MessageText.CANCEL_EDITING_COMMAND).append(MessageText.NEW_LINE);

        }
        reply.append(MessageText.ENTER_LOCATION);
        SendMessage sendMessage = new SendMessage(update.getMessage().getChatId().toString(),
                reply.toString());
        sendMessage.setReplyMarkup(locationKeyboard.getReplyButtons());
        return new Reply(sendMessage, false);
    }
}

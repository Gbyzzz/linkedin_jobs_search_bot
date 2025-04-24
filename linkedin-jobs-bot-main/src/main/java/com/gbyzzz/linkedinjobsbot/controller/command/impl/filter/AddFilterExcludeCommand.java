package com.gbyzzz.linkedinjobsbot.controller.command.impl.filter;

import com.gbyzzz.linkedinjobsbot.controller.command.Command;
import com.gbyzzz.linkedinjobsbot.dto.Reply;
import com.gbyzzz.linkedinjobsbot.modules.commons.values.MessageText;
import com.gbyzzz.linkedinjobsbot.modules.postgresdb.entity.SearchParams;
import com.gbyzzz.linkedinjobsbot.modules.postgresdb.entity.UserProfile;
import com.gbyzzz.linkedinjobsbot.modules.postgresdb.service.UserProfileService;
import com.gbyzzz.linkedinjobsbot.modules.redisdb.service.RedisService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;


@Component(MessageText.ADD_FILTER_EXCLUDE)
@AllArgsConstructor
public class AddFilterExcludeCommand implements Command {

    private final UserProfileService userProfileService;
    private final RedisService redisService;

    @Override
    public Reply execute(Update update) throws IOException {
        Long id = update.getMessage().getChatId();
        UserProfile userProfile = userProfileService.getUserProfileById(update.getMessage()
                .getChatId());
        SearchParams searchParams = redisService.getFromTempRepository(id);
        if (!update.getMessage().getText().equals(MessageText.PLUS)) {
            if(update.getMessage().getText().equals(MessageText.MINUS)) {
                searchParams.getFilterParams().setIncludeWordsInDescription(MessageText.EMPTY_STRING_ARRAY);
            } else {
                String[] keywords = update.getMessage().getText().split(MessageText.SPACES);
                searchParams.getFilterParams().setExcludeWordsFromTitle(keywords);
            }
            redisService.saveToTempRepository(searchParams, id);
        }
        userProfile.setBotState(UserProfile.BotState.NEW);
        userProfileService.save(userProfile);
        StringBuilder stringBuilder = new StringBuilder();
        if (searchParams.getId() != null) {
            stringBuilder.append(MessageText.CANCEL_EDITING_COMMAND)
                    .append(MessageText.ADD_FILTER_EXCLUDE_REPLY);
        } else {
            stringBuilder.append(MessageText.ADD_FILTER_EXCLUDE_REPLY);
        }
        return new Reply(new SendMessage(id.toString(),stringBuilder.toString()),false);
    }
}

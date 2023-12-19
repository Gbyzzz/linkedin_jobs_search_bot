package com.gbyzzz.linkedinjobsbot.controller.command.impl.filter;

import com.gbyzzz.linkedinjobsbot.controller.MessageText;
import com.gbyzzz.linkedinjobsbot.controller.command.Command;
import com.gbyzzz.linkedinjobsbot.dto.Reply;
import com.gbyzzz.linkedinjobsbot.entity.SearchParams;
import com.gbyzzz.linkedinjobsbot.entity.UserProfile;
import com.gbyzzz.linkedinjobsbot.service.RedisService;
import com.gbyzzz.linkedinjobsbot.service.UserProfileService;
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
                .getChatId()).get();
        SearchParams searchParams = redisService.getFromTempRepository(id);
        if (!update.getMessage().getText().equals(MessageText.PLUS)) {
            String [] keywords = update.getMessage().getText().split("[\\s\u00A0]+");
            searchParams.getFilterParams().setExcludeWordsFromTitle(keywords);
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

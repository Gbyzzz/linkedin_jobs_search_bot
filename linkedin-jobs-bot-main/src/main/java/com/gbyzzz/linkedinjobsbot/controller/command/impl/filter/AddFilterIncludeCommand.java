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

@Component(MessageText.ADD_FILTER_INCLUDE)
@AllArgsConstructor
public class AddFilterIncludeCommand implements Command {

    private final UserProfileService userProfileService;
    private final RedisService redisService;

    @Override
    public Reply execute(Update update) {
        Long id = update.getMessage().getChatId();

        UserProfile userProfile = userProfileService.getUserProfileById(update.getMessage()
                .getChatId());
        SearchParams searchParams = redisService.getFromTempRepository(id);
        String[] keywords = searchParams.getFilterParams().getIncludeWordsInDescription();
        if (!update.getMessage().getText().equals(MessageText.PLUS)) {
            if(update.getMessage().getText().equals(MessageText.MINUS)) {
                keywords = MessageText.EMPTY_STRING_ARRAY;
            } else {
                keywords = update.getMessage().getText().split(MessageText.SPACES);
            }
            searchParams.getFilterParams().setIncludeWordsInDescription(keywords);
            redisService.saveToTempRepository(searchParams, id);
        }
        userProfile.setBotState(UserProfile.BotState.ADD_FILTER_EXCLUDE);
        userProfileService.save(userProfile);
        StringBuilder reply = new StringBuilder(MessageText.ADD_FILTER_INCLUDE_REPLY_START);
        for (String word : keywords) {
            reply.append(word).append(MessageText.NEW_LINE);
        }
        reply.append(MessageText.NEW_LINE);
        if (searchParams.getFilterParams().getExcludeWordsFromTitle() != null) {
            reply.append(MessageText.EXCLUDE_EDIT_START);
            for (String exclude : searchParams.getFilterParams().getExcludeWordsFromTitle()) {
                reply.append(exclude).append(MessageText.SPACE);
            }
            reply.append(MessageText.NEW_LINE).append(MessageText.NEW_LINE)
                    .append(MessageText.ADD_FILTER_INCLUDE_REPLY_END);
            reply.append(MessageText.NEW_LINE).append(MessageText.TEXT_INPUT_EDIT_END)
                    .append(MessageText.CANCEL_EDITING_COMMAND);
        } else {
            reply.append(MessageText.ADD_FILTER_INCLUDE_REPLY_END);
        }
        return new Reply(new SendMessage(update.getMessage().getChatId().toString(),
                reply.toString()), false);
    }
}

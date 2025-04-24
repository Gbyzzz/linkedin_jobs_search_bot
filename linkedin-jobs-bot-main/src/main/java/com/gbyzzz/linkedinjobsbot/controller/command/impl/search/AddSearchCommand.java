package com.gbyzzz.linkedinjobsbot.controller.command.impl.search;

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

@Component(MessageText.ADD_SEARCH)
@AllArgsConstructor
public class AddSearchCommand implements Command {

    private final UserProfileService userProfileService;
    private final RedisService redisService;

    @Override
    public Reply execute(Update update) {
        Long id = update.hasMessage() ? update.getMessage().getChatId() :
                update.getCallbackQuery().getMessage().getChatId();
        UserProfile userProfile = userProfileService.getUserProfileById(id);
        userProfile.setBotState(UserProfile.BotState.ADD_KEYWORDS);
        userProfileService.save(userProfile);
        SearchParams searchParams = redisService.getFromTempRepository(id);
//        SendMessage sendMessage;
        StringBuilder stringBuilder;
        if(searchParams != null) {
            stringBuilder = new StringBuilder(MessageText.EDIT_KEYWORDS_REPLY);
            for (String keyword : searchParams.getKeywords()){
                stringBuilder.append(MessageText.TWO_SPACES).append(keyword)
                        .append(MessageText.NEW_LINE);
            }
            stringBuilder.append(MessageText.TEXT_INPUT_EDIT_END)
                    .append(MessageText.CANCEL_EDITING_COMMAND);
        } else {
            stringBuilder = new StringBuilder(MessageText.ADD_SEARCH_REPLY);
        }
        return new Reply(new SendMessage(id.toString(),stringBuilder.toString()),
                false);
    }
}

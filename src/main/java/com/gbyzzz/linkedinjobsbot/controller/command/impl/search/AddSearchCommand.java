package com.gbyzzz.linkedinjobsbot.controller.command.impl.search;

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

@Component(MessageText.ADD_SEARCH)
@AllArgsConstructor
public class AddSearchCommand implements Command {

    private final UserProfileService userProfileService;
    private final RedisService redisService;

    @Override
    public Reply execute(Update update) {
        Long id = update.hasMessage() ? update.getMessage().getChatId() :
                update.getCallbackQuery().getMessage().getChatId();
        UserProfile userProfile = userProfileService.getUserProfileById(id).get();
        userProfile.setBotState(UserProfile.BotState.ADD_KEYWORDS);
        userProfileService.save(userProfile);
        SearchParams searchParams = redisService.getFromTempRepository(id);
        SendMessage sendMessage;
        if(searchParams != null) {
            StringBuilder stringBuilder = new StringBuilder(MessageText.EDIT_KEYWORDS_REPLY);
            for (String keyword : searchParams.getKeywords()){
                stringBuilder.append(MessageText.TWO_SPACES).append(keyword)
                        .append(MessageText.NEW_LINE);
            }
            sendMessage = new SendMessage(id.toString(),
                    stringBuilder.toString());
        } else {
            sendMessage = new SendMessage(id.toString(),
                    MessageText.ADD_SEARCH_REPLY);
        }
        return new Reply(sendMessage,false);
    }
}

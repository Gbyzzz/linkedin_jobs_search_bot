package com.gbyzzz.linkedinjobsbot.controller.command.impl.search;

import com.gbyzzz.linkedinjobsbot.controller.MessageText;
import com.gbyzzz.linkedinjobsbot.controller.command.Command;
import com.gbyzzz.linkedinjobsbot.controller.command.keyboard.LocationKeyboard;
import com.gbyzzz.linkedinjobsbot.dto.Reply;
import com.gbyzzz.linkedinjobsbot.entity.SearchParams;
import com.gbyzzz.linkedinjobsbot.entity.UserProfile;
import com.gbyzzz.linkedinjobsbot.service.RedisService;
import com.gbyzzz.linkedinjobsbot.service.UserProfileService;
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
        SearchParams searchParams = new SearchParams();
        searchParams.setSearchFilters(new HashMap<>());
        String[] keywords = update.getMessage().getText().split(MessageText.SPACE);
        searchParams.setKeywords(keywords);
        UserProfile userProfile = userProfileService.getUserProfileById(
                update.getMessage().getChatId()).orElse(null);
        searchParams.setUserProfile(userProfile);

        redisService.saveToTempRepository(searchParams, update.getMessage().getChatId());
        userProfile.setBotState(UserProfile.BotState.ADD_LOCATION);
        userProfileService.save(userProfile);

        StringBuilder reply = new StringBuilder(MessageText.INPUTTED_KEYWORDS);
        reply.append(MessageText.NEW_LINE);
        for (String word : keywords) {
            reply.append(word).append(MessageText.NEW_LINE);
        }
        reply.append(MessageText.NEW_LINE);
        reply.append(MessageText.ENTER_LOCATION);
        SendMessage sendMessage = new SendMessage(update.getMessage().getChatId().toString(),
                reply.toString());
        sendMessage.setReplyMarkup(locationKeyboard.getReplyButtons());
        return new Reply(sendMessage, false);
    }
}

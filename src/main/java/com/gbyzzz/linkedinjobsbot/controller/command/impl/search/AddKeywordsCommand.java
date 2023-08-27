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
        Long id = update.getMessage().getChatId();
        UserProfile userProfile = userProfileService.getUserProfileById(id).orElse(null);
        SearchParams searchParams = redisService.getFromTempRepository(id);
        if (searchParams == null) {
            searchParams = new SearchParams();
            searchParams.setSearchFilters(new HashMap<>());
            searchParams.setUserProfile(userProfile);
        }
        String[] keywords = searchParams.getKeywords();
        if (!update.getMessage().getText().equals(MessageText.PLUS)) {
            keywords = update.getMessage().getText().split(MessageText.SPACE);
            searchParams.setKeywords(keywords);
            redisService.saveToTempRepository(searchParams, update.getMessage().getChatId());
        }
            userProfile.setBotState(UserProfile.BotState.ADD_LOCATION);
            userProfileService.save(userProfile);
        StringBuilder reply = new StringBuilder(MessageText.INPUTTED_KEYWORDS);
        reply.append(MessageText.NEW_LINE);
        for (String word : keywords) {
            reply.append(word).append(MessageText.NEW_LINE);
        }
        reply.append(MessageText.NEW_LINE);
        reply.append(MessageText.ENTER_LOCATION);
        if (searchParams.getLocation() != null) {
            reply.append(MessageText.NEW_LINE).append(MessageText.LOCATION_EDIT)
                    .append(searchParams.getLocation()).append(MessageText.NEW_LINE);
        }
        SendMessage sendMessage = new SendMessage(update.getMessage().getChatId().toString(),
                reply.toString());
        sendMessage.setReplyMarkup(locationKeyboard.getReplyButtons());
        return new Reply(sendMessage, false);
    }
}

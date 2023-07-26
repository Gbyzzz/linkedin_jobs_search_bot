package com.gbyzzz.linkedinjobsbot.controller.command.impl.search;

import com.gbyzzz.linkedinjobsbot.controller.command.Command;
import com.gbyzzz.linkedinjobsbot.entity.SearchParams;
import com.gbyzzz.linkedinjobsbot.entity.UserProfile;
import com.gbyzzz.linkedinjobsbot.service.SearchParamsService;
import com.gbyzzz.linkedinjobsbot.service.UserProfileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component("ADD_KEYWORDS")
@AllArgsConstructor
public class AddKeywordsCommand implements Command {

    private final SearchParamsService searchParamsService;
    private final UserProfileService userProfileService;

    @Override
    public SendMessage execute(Update update) {
        SearchParams searchParams = new SearchParams();
        String[] keywords = update.getMessage().getText().split(" ");
        searchParams.setKeywords(keywords);
        UserProfile userProfile = userProfileService.getUserProfileById(
                update.getMessage().getChatId()).orElse(null);
        searchParams.setUserProfile(userProfile);
//        searchParamsService.save(searchParams);

        searchParamsService.saveToTempRepository(searchParams, update.getMessage().getChatId());
        userProfile.setBotState(UserProfile.BotState.ADD_LOCATION);
        userProfileService.save(userProfile);

        StringBuilder reply = new StringBuilder("Your keywords are:\n");
        for (String word : keywords) {
            reply.append(word).append("\n");
        }
        reply.append("\nPlease enter Location");

        return new SendMessage(update.getMessage().getChatId().toString(), reply.toString());
    }
}

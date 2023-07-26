package com.gbyzzz.linkedinjobsbot.controller.command.impl.filter;

import com.gbyzzz.linkedinjobsbot.controller.command.Command;
import com.gbyzzz.linkedinjobsbot.entity.FilterParams;
import com.gbyzzz.linkedinjobsbot.entity.SearchParams;
import com.gbyzzz.linkedinjobsbot.entity.UserProfile;
import com.gbyzzz.linkedinjobsbot.service.SearchParamsService;
import com.gbyzzz.linkedinjobsbot.service.UserProfileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component("ADD_FILTER_INCLUDE")
@AllArgsConstructor
public class AddFilterIncludeCommand implements Command {

    private final UserProfileService userProfileService;
    private final SearchParamsService searchParamsService;

    @Override
    public SendMessage execute(Update update) {
        Long id = update.getMessage().getChatId();
        FilterParams filterParams = new FilterParams();
        String [] keywords = update.getMessage().getText().split(" ");
        filterParams.setInclude(keywords);
        UserProfile userProfile = userProfileService.getUserProfileById(update.getMessage().getChatId()).get();
//        searchParamsService.save(searchParams);
        SearchParams searchParams = searchParamsService.getFromTempRepository(id);
        searchParams.setFilterParams(filterParams);
        searchParamsService.saveToTempRepository(searchParams, id);
        userProfile.setBotState(UserProfile.BotState.ADD_FILTER_EXCLUDE);
        userProfileService.save(userProfile);

        StringBuilder reply = new StringBuilder("Your keywords, that should be included are:\n");
        for (String word : keywords) {
            reply.append(word).append("\n");
        }
        reply.append("\nPlease enter keywords, that should be in the results(separate them by space):");

        return new SendMessage(update.getMessage().getChatId().toString(), reply.toString());
    }
}

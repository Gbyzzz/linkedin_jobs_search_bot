package com.gbyzzz.linkedinjobsbot.controller.command.impl.filter;

import com.gbyzzz.linkedinjobsbot.controller.command.Command;
import com.gbyzzz.linkedinjobsbot.controller.command.impl.MakeFirstSearchCommand;
import com.gbyzzz.linkedinjobsbot.entity.SearchParams;
import com.gbyzzz.linkedinjobsbot.entity.UserProfile;
import com.gbyzzz.linkedinjobsbot.service.SearchParamsService;
import com.gbyzzz.linkedinjobsbot.service.UserProfileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;

@Component("ADD_FILTER_WORKPLACE")
@AllArgsConstructor
public class AddFilterWorkplaceCommand implements Command {

    private final UserProfileService userProfileService;
    private final SearchParamsService searchParamsService;
    private final MakeFirstSearchCommand makeFirstSearchCommand;

    @Override
    public SendMessage execute(Update update) throws IOException {
        Long id = update.getMessage().getChatId();
        String [] keywords = update.getMessage().getText().split(" ");
        UserProfile userProfile = userProfileService.getUserProfileById(update.getMessage()
                .getChatId()).get();
//        searchParamsService.save(searchParams);
        SearchParams searchParams = searchParamsService.getFromTempRepository(id);
        searchParams.getFilterParams().setWorkplace(keywords);
        searchParamsService.saveToTempRepository(searchParams, id);
        userProfile.setBotState(UserProfile.BotState.ADD_FILTER_WORKPLACE);
        userProfileService.save(userProfile);

        return makeFirstSearchCommand.execute(update);
    }
}
package com.gbyzzz.linkedinjobsbot.controller.command.impl.filter;

import com.gbyzzz.linkedinjobsbot.controller.command.Command;
import com.gbyzzz.linkedinjobsbot.service.SearchParamsService;
import com.gbyzzz.linkedinjobsbot.service.UserProfileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;


@Component("ADD_FILTER_JOB_TYPE")
@AllArgsConstructor
public class AddFilterJobTypeCommand implements Command {


    private final UserProfileService userProfileService;
    private final SearchParamsService searchParamsService;

    @Override
    public SendMessage execute(Update update) {
        return null;
    }
}

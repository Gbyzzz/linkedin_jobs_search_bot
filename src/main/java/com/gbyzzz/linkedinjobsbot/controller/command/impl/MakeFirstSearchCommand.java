package com.gbyzzz.linkedinjobsbot.controller.command.impl;

import com.gbyzzz.linkedinjobsbot.controller.command.Command;
import com.gbyzzz.linkedinjobsbot.entity.SearchParams;
import com.gbyzzz.linkedinjobsbot.service.JobService;
import com.gbyzzz.linkedinjobsbot.service.SearchParamsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;

@Component("MAKE_FIRST_SEARCH")
@AllArgsConstructor
public class MakeFirstSearchCommand implements Command {

    private final SearchParamsService searchParamsService;
    private final JobService jobService;

    @Override
    public SendMessage execute(Update update) throws IOException {
        SearchParams searchParams = searchParamsService.getFromTempRepository(update.getMessage().getChatId());
        searchParamsService.save(searchParams);
        jobService.makeScan(searchParams);
        jobService.filterResults(searchParams);


        return null;
    }
}

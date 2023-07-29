package com.gbyzzz.linkedinjobsbot.controller.command.impl;

import com.gbyzzz.linkedinjobsbot.controller.command.Command;
import com.gbyzzz.linkedinjobsbot.controller.command.keyboard.PaginationKeyboard;
import com.gbyzzz.linkedinjobsbot.entity.SearchParams;
import com.gbyzzz.linkedinjobsbot.service.JobService;
import com.gbyzzz.linkedinjobsbot.service.RedisService;
import com.gbyzzz.linkedinjobsbot.service.SearchParamsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.util.List;

@Component("MAKE_FIRST_SEARCH")
@AllArgsConstructor
public class MakeFirstSearchCommand implements Command {

    private final SearchParamsService searchParamsService;
    private final JobService jobService;
    private final PaginationKeyboard paginationKeyboard;
    private final RedisService redisService;

    @Override
    public SendMessage execute(Update update) throws IOException {
        Long id = update.getMessage().getChatId();
        SearchParams searchParams = redisService.getFromTempRepository(id);
        searchParams.getFilterParams().setSearchParams(searchParams);
        searchParamsService.save(searchParams);
        jobService.makeScan(searchParams);
        List<String> jobs = jobService.filterResults(searchParams);
        redisService.saveToTempRepository(jobs, id);
        SendMessage sendMessage = new SendMessage(id.toString(),
                "https://www.linkedin.com/jobs/view/" + jobs.get(0));
        sendMessage.setReplyMarkup(paginationKeyboard.getReplyButtons(0, jobs.size()));
        return sendMessage;
    }
}

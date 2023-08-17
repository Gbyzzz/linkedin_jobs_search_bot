package com.gbyzzz.linkedinjobsbot.service.scheduled;

import com.gbyzzz.linkedinjobsbot.controller.LinkedInJobsBot;
import com.gbyzzz.linkedinjobsbot.controller.command.impl.ListOfJobsCommand;
import com.gbyzzz.linkedinjobsbot.entity.SearchParams;
import com.gbyzzz.linkedinjobsbot.entity.converter.SendToEditMessageConverter;
import com.gbyzzz.linkedinjobsbot.service.*;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class ScheduledService {

    private final JobService jobService;

    private final SearchParamsService searchParamsService;
    private final ListOfJobsCommand listOfJobsCommand;
    private final SavedJobService savedJobService;
    private final LinkedInJobsBot linkedInJobsBot;
    private final SendToEditMessageConverter converter;

    @Scheduled(cron = "0 0/1 * * * ?")
    public void makeScan() throws IOException {
        System.out.println("Scheduled");
        List<SearchParams> searchParams = searchParamsService.findAll();
        if (!searchParams.isEmpty()) {
            Long id = searchParams.get(0).getUserProfile().getChatId();
            int initialSize = savedJobService.getNewJobsByUserId(id).size();
            for (SearchParams searchParam : searchParams) {
                if (searchParam.getSearchState().equals(SearchParams.SearchState.SUBSCRIBED)) {
                    jobService.makeScan(searchParam, 86200L);
                }
            }
            if (initialSize < savedJobService.getNewJobsByUserId(id).size()) {
                Update update = getUpdate(id);
                linkedInJobsBot.sendMessage(listOfJobsCommand
                        .execute(update).getSendMessage());
            }
        }
    }

    private Update getUpdate(Long id) {
        Update update = new Update();
        update.setCallbackQuery(new CallbackQuery());
        update.getCallbackQuery().setData("notify_SCHEDULED");
        update.getCallbackQuery().setMessage(new Message());
        update.getCallbackQuery().getMessage().setChat(new Chat());
        update.getCallbackQuery().getMessage().getChat().setId(id);
        return update;
    }
}

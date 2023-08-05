package com.gbyzzz.linkedinjobsbot.service.scheduled;

import com.gbyzzz.linkedinjobsbot.controller.LinkedInJobsBot;
import com.gbyzzz.linkedinjobsbot.controller.command.impl.WatchListOfJobsCommand;
import com.gbyzzz.linkedinjobsbot.entity.SearchParams;
import com.gbyzzz.linkedinjobsbot.entity.UserProfile;
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
    private final WatchListOfJobsCommand watchListOfJobsCommand;
    private final SavedJobService savedJobService;
    private final LinkedInJobsBot linkedInJobsBot;
    private final SendToEditMessageConverter converter;

    @Scheduled(cron = "0 0/1 * * * ?")
    public void makeScan() throws IOException {
        System.out.println("Scheduled");
        List<SearchParams> searchParams = searchParamsService.findAll();
        if (!searchParams.isEmpty()) {
            for (SearchParams searchParam : searchParams) {
                Long id = searchParam.getUserProfile().getChatId();
                int initialSize = savedJobService.getNewJobsByUserId(id).size();
                jobService.makeScan(searchParam, 86400L);

//                if (initialSize == 0) {
//                    Update update = getUpdate(id);
//                    linkedInJobsBot.sendMessage(watchListOfJobsCommand.execute(update)
//                            .getSendMessage());
//
//                } else
                    if (initialSize < savedJobService.getNewJobsByUserId(id).size()) {
                    Update update = getUpdate(id);
                    linkedInJobsBot.sendMessage(watchListOfJobsCommand
                            .execute(update).getSendMessage());
                }
            }
        }
    }

    private Update getUpdate(Long id) {
        Update update = new Update();
        update.setCallbackQuery(new CallbackQuery());
        update.getCallbackQuery().setData("notify");
        update.getCallbackQuery().setMessage(new Message());
        update.getCallbackQuery().getMessage().setChat(new Chat());
        update.getCallbackQuery().getMessage().getChat().setId(id);
        return update;
    }
}

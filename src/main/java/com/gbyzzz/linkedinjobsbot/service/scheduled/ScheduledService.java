package com.gbyzzz.linkedinjobsbot.service.scheduled;

import com.gbyzzz.linkedinjobsbot.controller.LinkedInJobsBot;
import com.gbyzzz.linkedinjobsbot.controller.MessageText;
import com.gbyzzz.linkedinjobsbot.controller.command.impl.ListOfJobsCommand;
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
    private final ListOfJobsCommand listOfJobsCommand;
    private final SavedJobService savedJobService;
    private final LinkedInJobsBot linkedInJobsBot;
    private final SendToEditMessageConverter converter;
    private final UserProfileService userProfileService;

    @Scheduled(cron = "0 0/30 * * * ?")
    public void makeScan() throws IOException {
        System.out.println("Scheduled");
//        List<SearchParams> searchParams = searchParamsService.findAll();
        List<UserProfile> userProfiles = userProfileService.getAll();
        if (!userProfiles.isEmpty()) {
            for(UserProfile user : userProfiles) {
                List<SearchParams> searchParams =
                        searchParamsService.findAllByUserId(user.getChatId());
                int initialSize = savedJobService.getNewJobsByUserId(user.getChatId()).size();
                if (!searchParams.isEmpty()) {
                    for (SearchParams searchParam : searchParams) {
                        if (searchParam.getSearchState().equals(SearchParams.SearchState.SUBSCRIBED)) {
                            jobService.makeScan(searchParam, 1440000L);
                        }
                    }
                    if (initialSize < savedJobService.getNewJobsByUserId(user.getChatId()).size()) {
                        Update update = getUpdate(user.getChatId());
                        linkedInJobsBot.sendMessage(listOfJobsCommand
                                .execute(update).getSendMessage());
                    }
                }
            }
        }
    }

    private Update getUpdate(Long id) {
        Update update = new Update();
        update.setCallbackQuery(new CallbackQuery());
        update.getCallbackQuery().setData(MessageText.NOTIFY + MessageText.BUTTON_VALUE_SEPARATOR +
                MessageText.SCHEDULED);
        update.getCallbackQuery().setMessage(new Message());
        update.getCallbackQuery().getMessage().setChat(new Chat());
        update.getCallbackQuery().getMessage().getChat().setId(id);
        return update;
    }
}

package com.gbyzzz.linkedinjobsbot.service.scheduled;

import com.gbyzzz.linkedinjobsbot.controller.LinkedInJobsBot;
import com.gbyzzz.linkedinjobsbot.controller.MessageText;
import com.gbyzzz.linkedinjobsbot.controller.command.impl.ListOfJobsCommand;
import com.gbyzzz.linkedinjobsbot.entity.SearchParams;
import com.gbyzzz.linkedinjobsbot.entity.UserProfile;
import com.gbyzzz.linkedinjobsbot.entity.converter.SendToEditMessageConverter;
import com.gbyzzz.linkedinjobsbot.service.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class ScheduledService {


    private final JobService jobService;

    private final SearchParamsService searchParamsService;
    private final ListOfJobsCommand listOfJobsCommand;
    private final SavedJobService savedJobService;
    private final LinkedInJobsBot linkedInJobsBot;
    private final SendToEditMessageConverter converter;
    private final UserProfileService userProfileService;

    public ScheduledService(JobService jobService, SearchParamsService searchParamsService,
                            ListOfJobsCommand listOfJobsCommand, SavedJobService savedJobService,
                            LinkedInJobsBot linkedInJobsBot, SendToEditMessageConverter converter,
                            UserProfileService userProfileService) {
        this.jobService = jobService;
        this.searchParamsService = searchParamsService;
        this.listOfJobsCommand = listOfJobsCommand;
        this.savedJobService = savedJobService;
        this.linkedInJobsBot = linkedInJobsBot;
        this.converter = converter;
        this.userProfileService = userProfileService;
    }

    @Scheduled(cron = "0 0/1 * * * ?")
    public void makeScan() throws IOException {
        System.out.println("Scheduled");
        List<UserProfile> userProfiles = userProfileService.getAll();
        System.out.println(userProfiles);
        AtomicInteger initialSize = new AtomicInteger();

//        Flux.fromIterable(userProfiles).map(user -> {
//            initialSize.set(savedJobService.getNewJobsByUserId(user.getChatId()).size());
//            return searchParamsService.findAllByUserId(user.getChatId())
//                .stream()
//                .collect(Collectors.toMap(
//                        SearchParams::getUserProfile,
//                        searchParams -> searchParamsService.findAllByUserId(searchParams.getUserProfile().getChatId())
//                                .stream()
//                                .anyMatch(searchParam -> {
//                                    try {
//                                        return jobService.makeScan(searchParam, 1440000L);
//                                    } catch (IOException e) {
//                                        throw new RuntimeException(e);
//                                    }
//                                })
//                ));}).subscribe(entry -> {
//                    entry.entrySet().stream().map(user -> {
//                        if (entry.get(user)) {
//                            if (initialSize.get() < savedJobService.getNewJobsByUserId(user.getKey().getChatId()).size()) {
//                                Update update = getUpdate(user.getKey().getChatId());
//                                try {
//                                    linkedInJobsBot.sendMessage(listOfJobsCommand.execute(update).getSendMessage());
//                                } catch (IOException e) {
//                                    throw new RuntimeException(e);
//                                }
//                            }
//                        }
//
//                    return null;});
//        });

        for (UserProfile user : userProfiles) {
            System.out.println("search for - " + user.getUsername() + ", chat id: " + user.getChatId());
            List<SearchParams> searchParams =
                    searchParamsService.findAllByUserId(user.getChatId());
            initialSize.set(savedJobService.getNewJobsByUserId(user.getChatId()).size());
            if (!searchParams.isEmpty()) {
                for (SearchParams searchParam : searchParams) {

//                        jobService.makeScan(searchParam, 1440000L);
                }
                if (initialSize.get() < savedJobService.getNewJobsByUserId(user.getChatId()).size()) {
                    Update update = getUpdate(user.getChatId());
                    linkedInJobsBot.sendMessage(listOfJobsCommand
                            .execute(update).getSendMessage());
                }
            }
        }
    }


    private Update getUpdate(Long id) {
//        SendMessage message = SendMessage // Create a message object
//                .builder()
//                .chatId(id)
//                .text(MessageText.NOTIFY + MessageText.BUTTON_VALUE_SEPARATOR +
//                        MessageText.SCHEDULED)
//                .build();
        Update update = new Update();
//        update.setCallbackQuery(new CallbackQuery());
//        update.getCallbackQuery().setData(MessageText.NOTIFY + MessageText.BUTTON_VALUE_SEPARATOR +
//                MessageText.SCHEDULED);
//        update.getCallbackQuery().setMessage(new Message());
//        ((Message)update.getCallbackQuery().getMessage()).setChat(new Chat);
//        ((Message)update.getCallbackQuery().getMessage()).getChat().setId(id);
        return update;
    }
}

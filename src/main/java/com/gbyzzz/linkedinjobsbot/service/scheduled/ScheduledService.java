package com.gbyzzz.linkedinjobsbot.service.scheduled;

import com.gbyzzz.linkedinjobsbot.controller.LinkedInJobsBot;
import com.gbyzzz.linkedinjobsbot.controller.command.impl.WatchListOfJobsCommand;
import com.gbyzzz.linkedinjobsbot.entity.SearchParams;
import com.gbyzzz.linkedinjobsbot.entity.UserProfile;
import com.gbyzzz.linkedinjobsbot.service.JobService;
import com.gbyzzz.linkedinjobsbot.service.RedisService;
import com.gbyzzz.linkedinjobsbot.service.SearchParamsService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ScheduledService {

    private final JobService jobService;
    private final SearchParamsService searchParamsService;
    private final RedisService redisService;
    private final WatchListOfJobsCommand watchListOfJobsCommand;
    private final LinkedInJobsBot linkedInJobsBot;

    @Scheduled(cron = "0 0/5 * * * ?")
    public void makeScan() throws IOException {
        System.out.println("Scheduled");
        List<SearchParams> searchParams = searchParamsService.findAll();
        if (!searchParams.isEmpty()) {
            for (SearchParams searchParam : searchParams) {
                Long id = searchParam.getUserProfile().getChatId();
                if (searchParam.getUserProfile().getBotState().equals(UserProfile.BotState.SUBSCRIBED)) {
                    jobService.makeScan(searchParam, 86400L);
                    List<String> newJobs = jobService.filterResults(searchParam);
                    if (newJobs.size() > 0) {
                        List<String> list = null;
                        try {
                            list = redisService.getListFromTempRepository(id);
                        } catch (Exception e) {

                        }
                        if (list != null && !list.isEmpty()) {
                            List<String> finalList = list;
                            List<String> differences = newJobs.stream()
                                    .filter(element -> !finalList.contains(element))
                                    .collect(Collectors.toList());
                            list.addAll(differences);
                            redisService.saveToTempRepository(list, id);
                        } else {
                            redisService.saveToTempRepository(newJobs, id);
                            Update update = new Update();
                            update.setCallbackQuery(new CallbackQuery());
                            update.getCallbackQuery().setData("notify");
                            update.getCallbackQuery().setMessage(new Message());
                            update.getCallbackQuery().getMessage().setChat(new Chat());
                            update.getCallbackQuery().getMessage().getChat().setId(id);
                            linkedInJobsBot.sendMessage(watchListOfJobsCommand.execute(update));

                        }
                    }
                }
            }
        }
    }
}

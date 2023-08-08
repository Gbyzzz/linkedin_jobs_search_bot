package com.gbyzzz.linkedinjobsbot.controller.command.impl;

import com.gbyzzz.linkedinjobsbot.controller.command.Command;
import com.gbyzzz.linkedinjobsbot.controller.command.keyboard.PaginationKeyboard;
import com.gbyzzz.linkedinjobsbot.dto.Reply;
import com.gbyzzz.linkedinjobsbot.entity.SavedJob;
import com.gbyzzz.linkedinjobsbot.entity.SearchParams;
import com.gbyzzz.linkedinjobsbot.entity.UserProfile;
import com.gbyzzz.linkedinjobsbot.service.*;
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
    private final SavedJobService savedJobService;
    private final UserProfileService userProfileService;

    @Override
    public Reply execute(Update update) throws IOException {
        Long id = update.getMessage().getChatId();
        SearchParams searchParams = redisService.getFromTempRepository(id);
        searchParams.getFilterParams().setSearchParams(searchParams);
        SendMessage sendMessage;
        if(!searchParamsService.existSearchParam(searchParams)) {
            jobService.makeScan(searchParams, null);
            UserProfile userProfile = userProfileService.getUserProfileById(id).get();
            userProfile.setBotState(UserProfile.BotState.NEW);
            userProfileService.save(userProfile);
            List<SavedJob> jobs = savedJobService.getNewJobsByUserId(id);
            if (!jobs.isEmpty()) {
                sendMessage = new SendMessage(id.toString(),
                        "New jobs:\nhttps://www.linkedin.com/jobs/view/"
                                + jobs.get(0).getJobId() + "\n1 of " + jobs.size());
                sendMessage.setReplyMarkup(paginationKeyboard.getReplyButtons(0, jobs.size(),
                        UserProfile.BotState.NEW.name()));
            } else {
                sendMessage = new SendMessage(id.toString(), "Nothing was found. Please check" +
                        " your search params or wait, maybe something will come up");
            }
        } else {
            sendMessage = new SendMessage(id.toString(), "You already have these search params");
        }
        searchParamsService.save(searchParams);
        redisService.deleteFromTempRepository(id);
        return new Reply(sendMessage, false);
    }
}

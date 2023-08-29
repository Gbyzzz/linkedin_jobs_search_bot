package com.gbyzzz.linkedinjobsbot.controller.command.impl;

import com.gbyzzz.linkedinjobsbot.controller.MessageText;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component(MessageText.MAKE_FIRST_SEARCH)
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
            if(searchParams.getSavedJobs() == null) {
                searchParams.setSavedJobs(new HashSet<>());
                searchParams.setSearchState(SearchParams.SearchState.NEW);
                searchParams = searchParamsService.save(searchParams);
            } else {
                searchParams.setSavedJobs(searchParamsService.findById(searchParams.getId()).getSavedJobs());
                Set<SavedJob> jobsToDelete = deleteNewJobs(searchParams.getSavedJobs());
                searchParams.setSearchState(SearchParams.SearchState.NEW);
                searchParams = searchParamsService.save(searchParams);
                savedJobService.deleteAll(jobsToDelete);
            }
//            searchParams.setSearchState(SearchParams.SearchState.NEW);
//            searchParams = searchParamsService.save(searchParams);
            jobService.makeScan(searchParams, null);
            UserProfile userProfile = userProfileService.getUserProfileById(id).get();
            userProfile.setBotState(UserProfile.BotState.NEW);
            userProfileService.save(userProfile);
            List<SavedJob> jobs = savedJobService.getNewJobsByUserId(id);
            if (!jobs.isEmpty()) {
                sendMessage = new SendMessage(id.toString(),
                        MessageText.makeNewJobsReply(0, jobs));
                sendMessage.setReplyMarkup(paginationKeyboard.getReplyButtons(0, jobs.size(),
                        UserProfile.BotState.NEW.name(), MessageText.ALL));
            } else {
                sendMessage = new SendMessage(id.toString(),
                        MessageText.MAKE_FIRST_SEARCH_NO_RESULTS);
            }
            searchParams = searchParamsService.findById(searchParams.getId());
            searchParams.setSearchState(SearchParams.SearchState.SUBSCRIBED);
            searchParamsService.save(searchParams);
        } else {
            sendMessage = new SendMessage(id.toString(),
                    MessageText.MAKE_FIRST_SEARCH_PARAMS_ALREADY_EXISTS);
        }
        redisService.deleteFromTempRepository(id);
        return new Reply(sendMessage, false);
    }

    private Set<SavedJob> deleteNewJobs(Set<SavedJob> jobs){
        Set<SavedJob> jobsToDelete = new HashSet<>();
        Set<SavedJob> jobsToDeleteForever = new HashSet<>();
        for(SavedJob job : jobs){
            if(job.getReplyState().equals(SavedJob.ReplyState.NEW_JOB)){
               jobsToDelete.add(job);
               if(job.getSearchParams().size()==1){
                   jobsToDeleteForever.add(job);
               }
            }
        }
        jobs.removeAll(jobsToDelete);
        return jobsToDeleteForever;
    }
}

package com.gbyzzz.linkedinjobsbot.controller.command.impl;

import com.gbyzzz.linkedinjobsbot.controller.command.Command;
import com.gbyzzz.linkedinjobsbot.dto.Reply;
import com.gbyzzz.linkedinjobsbot.modules.commons.values.MessageText;
import com.gbyzzz.linkedinjobsbot.modules.kafka.service.KafkaService;
import com.gbyzzz.linkedinjobsbot.modules.postgresdb.dto.mapper.SearchParamsDTOMapper;
import com.gbyzzz.linkedinjobsbot.modules.postgresdb.dto.mapper.SearchParamsTimeRangeDTOMapper;
import com.gbyzzz.linkedinjobsbot.modules.postgresdb.entity.SavedJob;
import com.gbyzzz.linkedinjobsbot.modules.postgresdb.entity.SearchParams;
import com.gbyzzz.linkedinjobsbot.modules.postgresdb.entity.UserProfile;
import com.gbyzzz.linkedinjobsbot.modules.postgresdb.service.SavedJobService;
import com.gbyzzz.linkedinjobsbot.modules.postgresdb.service.SearchParamsService;
import com.gbyzzz.linkedinjobsbot.modules.postgresdb.service.UserProfileService;
import com.gbyzzz.linkedinjobsbot.modules.redisdb.service.RedisService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Component(MessageText.MAKE_FIRST_SEARCH)
@AllArgsConstructor
public class MakeFirstSearchCommand implements Command {

    private final SearchParamsService searchParamsService;
    private final RedisService redisService;
    private final SavedJobService savedJobService;
    private final UserProfileService userProfileService;
    private final SearchParamsDTOMapper searchParamsDtoMapper;
    private final KafkaService kafkaService;
    private final SearchParamsTimeRangeDTOMapper searchParamsTimeRangeDTOMapper;


    @Override
    public Reply execute(Update update) throws IOException {
        Long id = update.getMessage().getChatId();
        SearchParams searchParams = searchParamsDtoMapper.toEntity(redisService.getFromTempRepository(id));
        searchParams.getFilterParams().setSearchParams(searchParams);
        searchParams.setUserProfile(userProfileService.getUserProfileById(id));
        SendMessage sendMessage;
        if(!searchParamsService.existSearchParam(searchParams)) {
            if(searchParams.getSavedJobs() == null) {
                searchParams.setSavedJobs(new HashSet<>());
                searchParamsService.save(searchParams);
            } else {
                searchParams.setSavedJobs(searchParamsService.findById(searchParams.getId()).getSavedJobs());
                Set<SavedJob> jobsToDelete = deleteNewJobs(searchParams.getSavedJobs());
                searchParamsService.save(searchParams);
                savedJobService.deleteAll(jobsToDelete);
            }
            UserProfile userProfile = userProfileService.getUserProfileById(id);
            userProfile.setBotState(UserProfile.BotState.NEW);
            userProfileService.save(userProfile);
            sendMessage = new SendMessage(id.toString(), MessageText.SAVED_SUCCESSFUL);
            kafkaService.sendMessage("to_search",
                    searchParamsTimeRangeDTOMapper.toDTO(searchParams, 0L));
        } else {
            sendMessage = new SendMessage(id.toString(),
                    MessageText.MAKE_FIRST_SEARCH_PARAMS_ALREADY_EXISTS);
        }
//        redisService.deleteFromTempRepository(id);
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

package com.gbyzzz.linkedinjobsbot.service.impl;

import com.gbyzzz.linkedinjobsbot.controller.LinkedInJobsBot;
import com.gbyzzz.linkedinjobsbot.modules.commons.values.MessageText;
import com.gbyzzz.linkedinjobsbot.modules.dto.dto.SearchParamsTimeRangeDTO;
import com.gbyzzz.linkedinjobsbot.modules.mongodb.service.JobService;
import com.gbyzzz.linkedinjobsbot.modules.postgresdb.entity.SavedJob;
import com.gbyzzz.linkedinjobsbot.modules.postgresdb.entity.SearchParams;
import com.gbyzzz.linkedinjobsbot.modules.postgresdb.service.SavedJobService;
import com.gbyzzz.linkedinjobsbot.modules.postgresdb.service.SearchParamsService;
import com.gbyzzz.linkedinjobsbot.service.MessageService;
import com.gbyzzz.linkedinjobsbot.service.NewJobService;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class NewJobServiceImpl implements NewJobService {

    private final SearchParamsService searchParamsService;
    private final JobService jobService;
    private final SavedJobService savedJobService;
    private final MessageService messageService;
    private final LinkedInJobsBot linkedInJobsBot;

    @Override
    @KafkaListener(
            topics = "${application.kafka.topic.to_check_if_new}",
            groupId = "groupId",
            containerFactory = "kafkaListenerContainerFactory")
    public void checkIfNew(SearchParamsTimeRangeDTO searchParamsDTO) {
        System.out.println("Search id: " + searchParamsDTO.id());
        String include = MessageText.INCLUDE_REGEX_START + String.join(MessageText.REGEX_SEPARATOR,
                processKeywords(searchParamsDTO.filterParams().getIncludeWordsInDescription())) +
                MessageText.INCLUDE_REGEX_END;

        String exclude = MessageText.EXCLUDE_REGEX_START + String.join(MessageText.REGEX_SEPARATOR,
                searchParamsDTO.filterParams().getExcludeWordsFromTitle()) + MessageText.EXCLUDE_REGEX_END;
        Set<String> foundJobs = jobService.findJobsIncludingAndExcludingWords(include, exclude,
                searchParamsDTO.id()).stream().map(job -> job.getId().toString()).collect(Collectors.toSet());
        System.out.println("After filtering:" + foundJobs.size());
        Set<String> savedJobs = savedJobService.getJobsByUserId(searchParamsDTO.userId()).stream().map(job ->
                job.getJobId().toString()).collect(Collectors.toSet());
        Set<String> toSave = new HashSet<>(foundJobs);
        toSave.removeAll(savedJobs);
        if (!toSave.isEmpty()) {
            List<String> jobDTO = new ArrayList<>(toSave);
            List<SavedJob> jobsToSave = getNewJobsToSave(jobDTO, searchParamsService.findById(searchParamsDTO.id()));
            savedJobService.saveAll(jobsToSave);
            List<SavedJob> jobs = savedJobService.getNewJobsByUserId(searchParamsDTO.userId());
            SendMessage sendMessage = messageService.getNewJobByUserId(searchParamsDTO.userId(),
                    new String[]{MessageText.NEW, MessageText.FIRST, MessageText.ALL});
            linkedInJobsBot.sendMessage(sendMessage);
        }
    }

    private String[] processKeywords(String[] keywords) {
        Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);


        for (int i = 0; i < keywords.length; i++) {
            Matcher m = p.matcher(keywords[i]);
            if (m.find()) {
                char[] characters = keywords[i].toCharArray();
                StringBuilder stringBuilder = new StringBuilder(MessageText.ALL_WORD_REGEX);
                for (char character : characters) {
                    if (p.matcher(String.valueOf(character)).find()) {
                        stringBuilder.append(MessageText.OPPOSITE_SLASH).append(character);
                    } else {
                        stringBuilder.append(character);
                    }
                }
                stringBuilder.append(MessageText.ALL_WORD_REGEX);
                keywords[i] = stringBuilder.toString();
            }
        }
        return keywords;
    }

    private List<SavedJob> getNewJobsToSave(List<String> jobs, SearchParams searchParams) {
        List<SavedJob> newJobs = new ArrayList<>();
        for (String newJob : jobs) {
            if (!savedJobService.existsJobByIdAndUserId(searchParams.getId(), searchParams.getUserProfile().getChatId())) {
                newJobs.add(new SavedJob(null, Long.parseLong(newJob), SavedJob.ReplyState.NEW_JOB,
                        null, searchParams.getUserProfile(), new HashSet<>() {{
                    add(searchParams);
                }}));
            }

        }
        return newJobs;
    }
}
package com.gbyzzz.linkedinjobsbot.controller.command.impl;

import com.gbyzzz.linkedinjobsbot.controller.command.Command;
import com.gbyzzz.linkedinjobsbot.controller.command.keyboard.PaginationKeyboard;
import com.gbyzzz.linkedinjobsbot.entity.SavedJob;
import com.gbyzzz.linkedinjobsbot.service.RedisService;
import com.gbyzzz.linkedinjobsbot.service.SavedJobService;
import com.gbyzzz.linkedinjobsbot.service.UserProfileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Component("WATCH_LIST_OF_JOBS")
@AllArgsConstructor
public class WatchListOfJobsCommand implements Command {

    private final SavedJobService savedJobService;
    private final UserProfileService userProfileService;
    private final PaginationKeyboard paginationKeyboard;

    @Override
    public SendMessage execute(Update update) throws IOException {
        Long id = update.getCallbackQuery().getMessage().getChatId();
        String[] command = update.getCallbackQuery().getData().split("_");
        SendMessage sendMessage = null;
        List<String> jobs = getNewJobs(id);

        switch (command[0]) {
            case "next", "previous" -> {sendMessage = new SendMessage(id.toString(),
                    "https://www.linkedin.com/jobs/view/" +
                            jobs.get(Integer.parseInt(command[1])) + "\n" +
                            (Integer.parseInt(command[1])+1) + " of " +
                            jobs.size());
            sendMessage.setReplyMarkup(paginationKeyboard.getReplyButtons(
                    Integer.parseInt(command[1]), jobs.size()));
            }
            case "apply" -> {
                SavedJob savedJob = savedJobService.getJobById(Long.valueOf(jobs.get(
                        Integer.parseInt(command[1]))));
                savedJobService.saveJob(savedJob);
                savedJob.setApplied(true);
                savedJob.setReplyState(SavedJob.ReplyState.APPLIED);
                savedJob.setDateApplied(new Date(System.currentTimeMillis()));
                savedJobService.saveJob(savedJob);
                jobs = getNewJobs(id);
                if (!jobs.isEmpty()) {
                    sendMessage = new SendMessage(id.toString(),
                            "https://www.linkedin.com/jobs/view/" +
                                    jobs.get(Integer.parseInt(command[1])) + "\n" +
                                    (Integer.parseInt(command[1]) + 1) + " of " +
                                    jobs.size());
                    sendMessage.setReplyMarkup(paginationKeyboard.getReplyButtons(
                            Integer.parseInt(command[1]), jobs.size()));
                } else {
                    sendMessage = new SendMessage(id.toString(), "Nothing left, we will " +
                            "notify if something will appear.\nStay tuned!");
                }
            }
            case "delete" -> {
                SavedJob savedJob = savedJobService.getJobById(Long.valueOf(jobs.get(
                        Integer.parseInt(command[1]))));
                savedJobService.saveJob(savedJob);
                savedJob.setDeleted(true);
                savedJob.setReplyState(SavedJob.ReplyState.DELETED);
                savedJobService.saveJob(savedJob);
                jobs = getNewJobs(id);
                if (!jobs.isEmpty()) {
                sendMessage = new SendMessage(id.toString(),
                        "https://www.linkedin.com/jobs/view/" +
                                jobs.get(Integer.parseInt(command[1])) + "\n" +
                                (Integer.parseInt(command[1])+1) + " of " +
                                jobs.size());
                sendMessage.setReplyMarkup(paginationKeyboard.getReplyButtons(
                        Integer.parseInt(command[1]), jobs.size()));
                } else {
                    sendMessage = new SendMessage(id.toString(), "Nothing left, we will " +
                            "notify if something will appear.\nStay tuned!");
                }
            }
            case "notify" -> {sendMessage = new SendMessage(id.toString(),
                    "https://www.linkedin.com/jobs/view/" +
                            jobs.get(0) + "\n1 of " + jobs.size());
                sendMessage.setReplyMarkup(paginationKeyboard.getReplyButtons(0,
                        jobs.size()));
            }

        }
        return sendMessage;
    }

    private List<String> getNewJobs(Long id){
        return savedJobService.getNewJobsByUserId(id).stream().map(
                (savedJob -> savedJob.getJobId().toString())
        ).toList();
    }
}

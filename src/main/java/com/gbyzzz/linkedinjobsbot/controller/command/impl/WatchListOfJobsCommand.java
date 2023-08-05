package com.gbyzzz.linkedinjobsbot.controller.command.impl;

import com.gbyzzz.linkedinjobsbot.controller.command.Command;
import com.gbyzzz.linkedinjobsbot.controller.command.keyboard.PaginationKeyboard;
import com.gbyzzz.linkedinjobsbot.dto.Reply;
import com.gbyzzz.linkedinjobsbot.entity.SavedJob;
import com.gbyzzz.linkedinjobsbot.entity.UserProfile;
import com.gbyzzz.linkedinjobsbot.service.RedisService;
import com.gbyzzz.linkedinjobsbot.service.SavedJobService;
import com.gbyzzz.linkedinjobsbot.service.UserProfileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component("WATCH_LIST_OF_JOBS")
@AllArgsConstructor
public class WatchListOfJobsCommand implements Command {

    private final SavedJobService savedJobService;
    private final UserProfileService userProfileService;
    private final PaginationKeyboard paginationKeyboard;

    @Override
    public Reply execute(Update update) throws IOException {
        Long id = update.getCallbackQuery().getMessage().getChatId();
        String[] command = update.getCallbackQuery().getData().split("_");
        SendMessage sendMessage = null;
        UserProfile.BotState botState = userProfileService.getUserProfileById(id)
                .get().getBotState();
        List<SavedJob> jobs = new ArrayList<>();
        if (botState.name().equals("LIST_NEW_JOBS")) {
            jobs = savedJobService.getNewJobsByUserId(id);
        } else if (botState.name().equals("LIST_APPLIED_JOBS")) {
            jobs = savedJobService.getAppliedJobsByUserId(id);
        }
        Long targetId = null;
        if (command.length > 1) {
            targetId = jobs.get(Integer.parseInt(command[1])).getJobId();
        }


        switch (command[0]) {
            case "next", "previous" -> {
                int index = Integer.parseInt(command[1]);
                sendMessage = makeReply(jobs, index, botState, id);
                sendMessage.setReplyMarkup(paginationKeyboard.getReplyButtons(
                        Integer.parseInt(command[1]), jobs.size(), botState));
            }
            case "apply" -> {
                SavedJob savedJob = savedJobService.getJobById(targetId);
                savedJobService.saveJob(savedJob);
                savedJob.setReplyState(SavedJob.ReplyState.APPLIED);
                savedJob.setDateApplied(new Date(System.currentTimeMillis()));
                savedJobService.saveJob(savedJob);
                jobs = savedJobService.getNewJobsByUserId(id);
                int index = (jobs.size() - 1) > Integer.parseInt(command[1]) ?
                        Integer.parseInt(command[1]) :
                        Integer.parseInt(command[1]) - 1;
                sendMessage = makeReply(jobs, index, botState, id);
            }
            case "delete" -> {
                SavedJob savedJob = savedJobService.getJobById(targetId);
                savedJob.setReplyState(SavedJob.ReplyState.DELETED);
                savedJobService.saveJob(savedJob);
                jobs = savedJobService.getNewJobsByUserId(id);
                int index = (jobs.size() - 1) > Integer.parseInt(command[1]) ?
                        Integer.parseInt(command[1]) :
                        Integer.parseInt(command[1]) - 1;
                sendMessage = makeReply(jobs, index, botState, id);
            }
            case "rejected" -> {
                SavedJob savedJob = savedJobService.getJobById(targetId);
                savedJobService.saveJob(savedJob);
                savedJob.setReplyState(SavedJob.ReplyState.REJECTED);
                savedJobService.saveJob(savedJob);
                jobs = savedJobService.getAppliedJobsByUserId(id);
                int index = (jobs.size() - 1) > Integer.parseInt(command[1]) ?
                        Integer.parseInt(command[1]) :
                        Integer.parseInt(command[1]) - 1;
                sendMessage = makeReply(jobs, index, botState, id);
            }
            case "notify" -> {
                    sendMessage = makeReply(jobs, 0, botState, id);
            }
        }
        return new Reply(sendMessage, true);
    }

    private SendMessage makeReply(List<SavedJob> jobs, int index, UserProfile.BotState state,
                                  Long id) {
        SendMessage sendMessage;
        if (!jobs.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            if (state.name().equals("LIST_NEW_JOBS")) {
                stringBuilder.append("New jobs:\n");
                stringBuilder.append("https://www.linkedin.com/jobs/view/")
                        .append(jobs.get(index).getJobId())
                        .append("\n")
                        .append(index + 1)
                        .append(" of ")
                        .append(jobs.size());
            } else if (state.name().equals("LIST_APPLIED_JOBS")) {
                stringBuilder.append("Applied jobs:\n");
                stringBuilder.append("https://www.linkedin.com/jobs/view/")
                        .append(jobs.get(index).getJobId())
                        .append("\nApplied on - ")
                        .append(new SimpleDateFormat("dd-MMM-yyyy").format(jobs
                                .get(index).getDateApplied()))
                        .append("\n")
                        .append(index + 1)
                        .append(" of ")
                        .append(jobs.size());
            }

            sendMessage = new SendMessage(id.toString(),
                    stringBuilder.toString());
            sendMessage.setReplyMarkup(paginationKeyboard.getReplyButtons(
                    index, jobs.size(), state));
        } else {
            sendMessage = new SendMessage(id.toString(), "Nothing left, we will " +
                    "notify if something will appear.\nStay tuned!");
        }
        return sendMessage;
    }
}

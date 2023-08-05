package com.gbyzzz.linkedinjobsbot.controller.command.impl;

import com.gbyzzz.linkedinjobsbot.controller.command.Command;
import com.gbyzzz.linkedinjobsbot.controller.command.keyboard.PaginationKeyboard;
import com.gbyzzz.linkedinjobsbot.dto.Reply;
import com.gbyzzz.linkedinjobsbot.entity.SavedJob;
import com.gbyzzz.linkedinjobsbot.entity.UserProfile;
import com.gbyzzz.linkedinjobsbot.service.SavedJobService;
import com.gbyzzz.linkedinjobsbot.service.UserProfileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

@Component("GET_APPLIED_JOBS")
@AllArgsConstructor
public class GetAppliedJobs implements Command {

//    private final SavedJobService savedJobService;
//    private final PaginationKeyboard paginationKeyboard;
//    private final UserProfileService userProfileService;
//
//    @Override
//    public Reply execute(Update update) throws IOException {
//        Long id = update.getMessage().getChatId();
//        List<SavedJob> jobs = savedJobService.getAppliedJobsByUserId(id);
//        StringBuilder stringBuilder = new StringBuilder();
//        if (!jobs.isEmpty()) {
//            for (int i = 1; i <= jobs.size(); i++) {
//                SavedJob job = jobs.get(i-1);
//                stringBuilder.append(i)
//                        .append(". https://www.linkedin.com/jobs/view/")
//                        .append(job.getJobId())
//                        .append("\n")
//                        .append("Applied on - ")
//                        .append(new SimpleDateFormat("dd-MMM-yyyy").format(job.getDateApplied()))
//                        .append("\n");
//            }
//        } else {
//            stringBuilder.append("No jobs applied");
//        }
//        return new Reply(new SendMessage(id.toString(), stringBuilder.toString()), false);
//    }

    private static final String REPLY = "No new jobs at the moment, if something comes up we will" +
            "notice you";

    private final SavedJobService savedJobService;
    private final PaginationKeyboard paginationKeyboard;
    private final UserProfileService userProfileService;


    @Override
    public Reply execute(Update update) throws IOException {
        Long id = update.getMessage().getChatId();
        SendMessage sendMessage;
        List<SavedJob> jobs = savedJobService.getAppliedJobsByUserId(id);

        if (!jobs.isEmpty()) {
            UserProfile userProfile = userProfileService.getUserProfileById(id).get();
            userProfile.setBotState(UserProfile.BotState.LIST_APPLIED_JOBS);
            userProfileService.save(userProfile);
            sendMessage = new SendMessage(id.toString(),
                    "New jobs:\nhttps://www.linkedin.com/jobs/view/" + jobs.get(0).getJobId()
                            + "\nApplied on - " + new SimpleDateFormat("dd-MMM-yyyy")
                            .format(jobs.get(0).getDateApplied())
                            + "\n1 of " + jobs.size());
            sendMessage.setReplyMarkup(paginationKeyboard.getReplyButtons(0, jobs.size(),
                    UserProfile.BotState.LIST_APPLIED_JOBS));
        } else {
            sendMessage = new SendMessage(id.toString(), REPLY);
        }
        return new Reply(sendMessage, false);
    }
}

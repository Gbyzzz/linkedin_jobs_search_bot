package com.gbyzzz.linkedinjobsbot.controller.command.impl;

import com.gbyzzz.linkedinjobsbot.controller.MessageText;
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
import java.util.Date;
import java.util.List;

@Component("GET_APPLIED_JOBS")
@AllArgsConstructor
public class GetAppliedJobs implements Command {

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
            userProfile.setBotState(UserProfile.BotState.APPLIED);
            userProfileService.save(userProfile);
            String date = new SimpleDateFormat(MessageText.DATE_FORMAT.getValue())
                    .format(jobs.get(0).getDateApplied());
            StringBuilder reply = new StringBuilder(MessageText.NEW_JOBS.getValue());

            reply.append(MessageText.NEW_LINE.getValue()).append(MessageText.JOB_URL.getValue())
                    .append(jobs.get(0).getJobId()).append(MessageText.NEW_LINE.getValue())
                    .append(MessageText.APPLIED_ON.getValue()).append(date)
                    .append(MessageText.NEW_LINE.getValue()).append(MessageText.ONE.getValue())
                    .append(MessageText.OF.getValue()).append(jobs.size());

            sendMessage = new SendMessage(id.toString(), reply.toString());
            sendMessage.setReplyMarkup(paginationKeyboard.getReplyButtons(0, jobs.size(),
                    UserProfile.BotState.APPLIED.name(), MessageText.ALL.getValue()));
        } else {
            sendMessage = new SendMessage(id.toString(),
                    MessageText.GET_APPLIED_JOBS_REPLY.getValue());
        }
        return new Reply(sendMessage, false);
    }
}

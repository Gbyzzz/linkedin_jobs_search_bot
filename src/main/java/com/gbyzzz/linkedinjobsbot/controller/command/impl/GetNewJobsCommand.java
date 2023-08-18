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
import java.util.List;

@Component(MessageText.GET_NEW_JOBS)
@AllArgsConstructor
public class GetNewJobsCommand implements Command {

    private final SavedJobService savedJobService;
    private final PaginationKeyboard paginationKeyboard;
    private final UserProfileService userProfileService;

    @Override
    public Reply execute(Update update) throws IOException {
        Long id = update.getMessage().getChatId();
        SendMessage sendMessage;
        List<SavedJob> jobs = savedJobService.getNewJobsByUserId(id);
        UserProfile userProfile = userProfileService.getUserProfileById(id).get();
        userProfile.setBotState(UserProfile.BotState.NEW);
        userProfileService.save(userProfile);
        if (!jobs.isEmpty()) {
            sendMessage = new SendMessage(id.toString(),MessageText.makeNewJobsReply(0, jobs));
            sendMessage.setReplyMarkup(paginationKeyboard.getReplyButtons(0, jobs.size(),
                    UserProfile.BotState.NEW.name(), MessageText.ALL));
        } else {
            sendMessage = new SendMessage(id.toString(), MessageText.GET_NEW_JOBS_REPLY);
        }
        return new Reply(sendMessage, false);
    }
}

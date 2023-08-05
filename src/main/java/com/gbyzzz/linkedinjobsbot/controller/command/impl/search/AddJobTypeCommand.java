package com.gbyzzz.linkedinjobsbot.controller.command.impl.search;

import com.gbyzzz.linkedinjobsbot.controller.command.Command;
import com.gbyzzz.linkedinjobsbot.controller.command.keyboard.ExperienceKeyboard;
import com.gbyzzz.linkedinjobsbot.controller.command.keyboard.JobTypeKeyboard;
import com.gbyzzz.linkedinjobsbot.controller.command.keyboard.WorkplaceKeyboard;
import com.gbyzzz.linkedinjobsbot.dto.Reply;
import com.gbyzzz.linkedinjobsbot.entity.SearchParams;
import com.gbyzzz.linkedinjobsbot.entity.UserProfile;
import com.gbyzzz.linkedinjobsbot.service.RedisService;
import com.gbyzzz.linkedinjobsbot.service.SearchParamsService;
import com.gbyzzz.linkedinjobsbot.service.UserProfileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.gbyzzz.linkedinjobsbot.controller.command.keyboard.JobTypeKeyboard.getJobTypeCallbackAction;
import static com.gbyzzz.linkedinjobsbot.controller.command.keyboard.JobTypeKeyboard.getJobTypeValue;
import static com.gbyzzz.linkedinjobsbot.controller.command.keyboard.WorkplaceKeyboard.setWorkplaceKeyboardFalse;

@Component("ADD_JOB_TYPE")
@AllArgsConstructor
public class AddJobTypeCommand implements Command {

    private final UserProfileService userProfileService;
    private final JobTypeKeyboard jobTypeKeyboard;
    private final WorkplaceKeyboard workplaceKeyboard;
    private final RedisService redisService;

    private static final String REPLY = "Now add job type:";
    private static final String REPLY_NEXT = "Now add workplace type:";

    @Override
    public Reply execute(Update update) {
        SendMessage sendMessage;
        Long id = update.getCallbackQuery().getMessage().getChatId();
        String data = update.getCallbackQuery().getData();
        Reply reply;
        if (data.equals("next")) {
            UserProfile userProfile = userProfileService.getUserProfileById(id).get();
            userProfile.setBotState(UserProfile.BotState.ADD_WORKPLACE);
            userProfileService.save(userProfile);
            sendMessage = new SendMessage(id.toString(), REPLY_NEXT);
            String jobType = getJobTypeValue();
            if(!jobType.isEmpty()) {
                SearchParams searchParams = redisService.getFromTempRepository(id);
                searchParams.getSearchFilters().put("jobType", jobType);
                redisService.saveToTempRepository(searchParams, id);
            }
            setWorkplaceKeyboardFalse();
            sendMessage.setReplyMarkup(workplaceKeyboard.getReplyButtons());
            reply = new Reply(sendMessage, false);
        } else {
            getJobTypeCallbackAction(data);
            sendMessage = new SendMessage(id.toString(), REPLY);
            sendMessage.setReplyMarkup(jobTypeKeyboard.getReplyButtons());
            reply = new Reply(sendMessage, true);
        }
        return reply;
    }


}

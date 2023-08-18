package com.gbyzzz.linkedinjobsbot.controller.command.impl.search;

import com.gbyzzz.linkedinjobsbot.controller.MessageText;
import com.gbyzzz.linkedinjobsbot.controller.command.Command;
import com.gbyzzz.linkedinjobsbot.controller.command.keyboard.JobTypeKeyboard;
import com.gbyzzz.linkedinjobsbot.controller.command.keyboard.WorkplaceKeyboard;
import com.gbyzzz.linkedinjobsbot.dto.Reply;
import com.gbyzzz.linkedinjobsbot.entity.SearchParams;
import com.gbyzzz.linkedinjobsbot.entity.UserProfile;
import com.gbyzzz.linkedinjobsbot.service.RedisService;
import com.gbyzzz.linkedinjobsbot.service.UserProfileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.gbyzzz.linkedinjobsbot.controller.command.keyboard.JobTypeKeyboard.getJobTypeCallbackAction;
import static com.gbyzzz.linkedinjobsbot.controller.command.keyboard.JobTypeKeyboard.getJobTypeValue;
import static com.gbyzzz.linkedinjobsbot.controller.command.keyboard.WorkplaceKeyboard.setWorkplaceKeyboardFalse;

@Component(MessageText.ADD_JOB_TYPE)
@AllArgsConstructor
public class AddJobTypeCommand implements Command {

    private final UserProfileService userProfileService;
    private final JobTypeKeyboard jobTypeKeyboard;
    private final WorkplaceKeyboard workplaceKeyboard;
    private final RedisService redisService;

    @Override
    public Reply execute(Update update) {
        SendMessage sendMessage;
        Long id = update.getCallbackQuery().getMessage().getChatId();
        String data = update.getCallbackQuery().getData();
        Reply reply;
        if (data.equals(MessageText.NEXT)) {
            UserProfile userProfile = userProfileService.getUserProfileById(id).get();
            userProfile.setBotState(UserProfile.BotState.ADD_WORKPLACE);
            userProfileService.save(userProfile);
            sendMessage = new SendMessage(id.toString(),
                    MessageText.ADD_JOB_TYPE_REPLY_NEXT);
            String jobType = getJobTypeValue();
            if(!jobType.isEmpty()) {
                SearchParams searchParams = redisService.getFromTempRepository(id);
                searchParams.getSearchFilters().put(MessageText.JOB_TYPE, jobType);
                redisService.saveToTempRepository(searchParams, id);
            }
            setWorkplaceKeyboardFalse();
            sendMessage.setReplyMarkup(workplaceKeyboard.getReplyButtons());
            reply = new Reply(sendMessage, false);
        } else {
            getJobTypeCallbackAction(data);
            sendMessage = new SendMessage(id.toString(), MessageText.ADD_JOB_TYPE_REPLY);
            sendMessage.setReplyMarkup(jobTypeKeyboard.getReplyButtons());
            reply = new Reply(sendMessage, true);
        }
        return reply;
    }


}

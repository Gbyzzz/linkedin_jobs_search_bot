package com.gbyzzz.linkedinjobsbot.controller.command.impl.search;

import com.gbyzzz.linkedinjobsbot.controller.command.Command;
import com.gbyzzz.linkedinjobsbot.controller.command.keyboard.JobTypeKeyboard;
import com.gbyzzz.linkedinjobsbot.controller.command.keyboard.WorkplaceKeyboard;
import com.gbyzzz.linkedinjobsbot.dto.Reply;
import com.gbyzzz.linkedinjobsbot.modules.commons.values.MessageText;
import com.gbyzzz.linkedinjobsbot.modules.postgresdb.entity.SearchParams;
import com.gbyzzz.linkedinjobsbot.modules.postgresdb.entity.UserProfile;
import com.gbyzzz.linkedinjobsbot.modules.postgresdb.service.UserProfileService;
import com.gbyzzz.linkedinjobsbot.modules.redisdb.service.RedisService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.gbyzzz.linkedinjobsbot.controller.command.keyboard.JobTypeKeyboard.getJobTypeCallbackAction;
import static com.gbyzzz.linkedinjobsbot.controller.command.keyboard.JobTypeKeyboard.getJobTypeValue;
import static com.gbyzzz.linkedinjobsbot.controller.command.keyboard.WorkplaceKeyboard.putWorkplaceValue;
import static com.gbyzzz.linkedinjobsbot.controller.command.keyboard.WorkplaceKeyboard.setWorkplaceKeyboardState;

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
        StringBuilder stringBuilder = new StringBuilder();
        SearchParams searchParams = redisService.getFromTempRepository(id);
        if (data.equals(MessageText.NEXT)) {
            UserProfile userProfile = userProfileService.getUserProfileById(id);
            userProfile.setBotState(UserProfile.BotState.ADD_WORKPLACE);
            userProfileService.save(userProfile);

            String jobType = getJobTypeValue();
            if (!jobType.isEmpty()) {
                searchParams.getSearchFilters().put(MessageText.JOB_TYPE, jobType);
                redisService.saveToTempRepository(searchParams, id);
            }
            setWorkplaceKeyboardState(false);
            if(searchParams.getId() != null) {
                stringBuilder.append(MessageText.CANCEL_EDITING_COMMAND)
                        .append(MessageText.ADD_JOB_TYPE_REPLY_NEXT);
            } else {
                stringBuilder.append(MessageText.ADD_JOB_TYPE_REPLY_NEXT);
            }
            sendMessage = new SendMessage(id.toString(), stringBuilder.toString());
            if (searchParams.getSearchFilters().get(MessageText.WORKPLACE_TYPE) != null) {
                putWorkplaceValue(searchParams.getSearchFilters().get(MessageText.WORKPLACE_TYPE));
            }
            sendMessage.setReplyMarkup(workplaceKeyboard.getReplyButtons());
            reply = new Reply(sendMessage, false);
        } else {
            getJobTypeCallbackAction(data);

            if(searchParams.getId() != null) {
                stringBuilder.append(MessageText.CANCEL_EDITING_COMMAND)
                        .append(MessageText.ADD_LOCATION_REPLY);
            } else {
                stringBuilder.append(MessageText.ADD_LOCATION_REPLY);
            }
            sendMessage = new SendMessage(id.toString(), stringBuilder.toString());
            sendMessage.setReplyMarkup(jobTypeKeyboard.getReplyButtons());
            reply = new Reply(sendMessage, true);
        }
        return reply;
    }
}

package com.gbyzzz.linkedinjobsbot.controller.command.impl.search;

import com.gbyzzz.linkedinjobsbot.controller.MessageText;
import com.gbyzzz.linkedinjobsbot.controller.command.Command;
import com.gbyzzz.linkedinjobsbot.controller.command.keyboard.ExperienceKeyboard;
import com.gbyzzz.linkedinjobsbot.controller.command.keyboard.JobTypeKeyboard;
import com.gbyzzz.linkedinjobsbot.dto.Reply;
import com.gbyzzz.linkedinjobsbot.entity.SearchParams;
import com.gbyzzz.linkedinjobsbot.entity.UserProfile;
import com.gbyzzz.linkedinjobsbot.service.RedisService;
import com.gbyzzz.linkedinjobsbot.service.UserProfileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.gbyzzz.linkedinjobsbot.controller.command.keyboard.ExperienceKeyboard.*;
import static com.gbyzzz.linkedinjobsbot.controller.command.keyboard.JobTypeKeyboard.*;

@Component(MessageText.ADD_EXPERIENCE)
@AllArgsConstructor
public class AddExperienceCommand implements Command {

    private final UserProfileService userProfileService;
    private final ExperienceKeyboard experienceKeyboard;
    private final JobTypeKeyboard jobTypeKeyboard;
    private final RedisService redisService;

    @Override
    public Reply execute(Update update) {
        SendMessage sendMessage;
        Long id = update.getCallbackQuery().getMessage().getChatId();
        Reply reply;

        String data = update.getCallbackQuery().getData();
        if (data.equals(MessageText.NEXT)) {
            UserProfile userProfile = userProfileService.getUserProfileById(id).get();
            userProfile.setBotState(UserProfile.BotState.ADD_JOB_TYPE);
            userProfileService.save(userProfile);

            String experience = getExperienceValue();
            if(!experience.isEmpty()) {
                SearchParams searchParams = redisService.getFromTempRepository(id);
                searchParams.getSearchFilters().put(MessageText.EXPERIENCE, experience);
                redisService.saveToTempRepository(searchParams, id);
            }
            sendMessage = new SendMessage(id.toString(), MessageText.ADD_EXPERIENCE_REPLY_NEXT);
            setJobTypeKeyboardFalse();
            sendMessage.setReplyMarkup(jobTypeKeyboard.getReplyButtons());
            reply = new Reply(sendMessage, false);
        } else {
            getExperienceCallbackAction(data);
            sendMessage = new SendMessage(id.toString(), MessageText.ADD_EXPERIENCE_REPLY);
            sendMessage.setReplyMarkup(experienceKeyboard.getReplyButtons());
            reply = new Reply(sendMessage, true);
        }

        return reply;
    }
}

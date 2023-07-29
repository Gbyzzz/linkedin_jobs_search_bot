package com.gbyzzz.linkedinjobsbot.controller.command.impl.search;

import com.gbyzzz.linkedinjobsbot.controller.command.Command;
import com.gbyzzz.linkedinjobsbot.controller.command.keyboard.ExperienceKeyboard;
import com.gbyzzz.linkedinjobsbot.controller.command.keyboard.JobTypeKeyboard;
import com.gbyzzz.linkedinjobsbot.entity.SearchParams;
import com.gbyzzz.linkedinjobsbot.entity.UserProfile;
import com.gbyzzz.linkedinjobsbot.service.RedisService;
import com.gbyzzz.linkedinjobsbot.service.SearchParamsService;
import com.gbyzzz.linkedinjobsbot.service.UserProfileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;

import static com.gbyzzz.linkedinjobsbot.controller.command.keyboard.ExperienceKeyboard.*;
import static com.gbyzzz.linkedinjobsbot.controller.command.keyboard.JobTypeKeyboard.*;

@Component("ADD_EXPERIENCE")
@AllArgsConstructor
public class AddExperienceCommand implements Command {

    private final UserProfileService userProfileService;
    private final ExperienceKeyboard experienceKeyboard;
    private final JobTypeKeyboard jobTypeKeyboard;
    private final RedisService redisService;

    private static final String REPLY = "Search parameters added\n" +
            "Now you can add additional search parameters, experience:";
    private static final String REPLY_NEXT = "Now add job type:";

    @Override
    public SendMessage execute(Update update) {
        SendMessage sendMessage;
        Long id = update.getCallbackQuery().getMessage().getChatId();

        String data = update.getCallbackQuery().getData();
        if (data.equals("next")) {
            UserProfile userProfile = userProfileService.getUserProfileById(id).get();
            userProfile.setBotState(UserProfile.BotState.ADD_JOB_TYPE);
            userProfileService.save(userProfile);
            SearchParams searchParams = (SearchParams) redisService.getFromTempRepository(id);
            HashMap<String, String> searchFilters = new HashMap<>(){{
                put("experience", getExperienceValue());
            }};
            searchParams.setSearchFilters(searchFilters);
            redisService.saveToTempRepository(searchParams, id);
            sendMessage = new SendMessage(id.toString(), REPLY_NEXT);
            setJobTypeKeyboardFalse();
            sendMessage.setReplyMarkup(jobTypeKeyboard.getReplyButtons());
        } else {
            getExperienceCallbackAction(data);
            sendMessage = new SendMessage(id.toString(), REPLY);
            sendMessage.setReplyMarkup(experienceKeyboard.getReplyButtons());
        }

        return sendMessage;
    }
}

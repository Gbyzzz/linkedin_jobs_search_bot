package com.gbyzzz.linkedinjobsbot.controller.command.impl.search;

import com.gbyzzz.linkedinjobsbot.controller.command.Command;
import com.gbyzzz.linkedinjobsbot.controller.command.keyboard.ExperienceKeyboard;
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
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import static com.gbyzzz.linkedinjobsbot.controller.command.keyboard.ExperienceKeyboard.*;

@Component("ADD_LOCATION")
@AllArgsConstructor
public class AddLocationCommand extends BotCommand implements Command {

    private final UserProfileService userProfileService;
    private final ExperienceKeyboard experienceKeyboard;
    private final RedisService redisService;
    private static final String REPLY_TEXT = "Search parameters added\n" +
            "Now you can add additional search parameters, experience :";

    @Override
    public Reply execute(Update update) {
        Long id = update.getCallbackQuery().getMessage().getChatId();
        SendMessage sendMessage;
        sendMessage = new SendMessage(id.toString(), REPLY_TEXT);
        SearchParams searchParams = redisService.getFromTempRepository(id);
        searchParams.setLocation(update.getCallbackQuery().getData());
        redisService.saveToTempRepository(searchParams, id);
        UserProfile userProfile = userProfileService
                .getUserProfileById(id).orElse(null);
        userProfile.setBotState(UserProfile.BotState.ADD_EXPERIENCE);
        userProfileService.save(userProfile);
        setExperienceKeyboardFalse();
        sendMessage.setReplyMarkup(experienceKeyboard.getReplyButtons());
        return new Reply(sendMessage, false);
    }
}

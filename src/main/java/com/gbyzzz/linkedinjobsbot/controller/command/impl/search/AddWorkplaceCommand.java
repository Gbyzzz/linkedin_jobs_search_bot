package com.gbyzzz.linkedinjobsbot.controller.command.impl.search;

import com.gbyzzz.linkedinjobsbot.controller.command.Command;
import com.gbyzzz.linkedinjobsbot.controller.command.keyboard.AfterAddingSearchKeyboard;
import com.gbyzzz.linkedinjobsbot.controller.command.keyboard.JobTypeKeyboard;
import com.gbyzzz.linkedinjobsbot.controller.command.keyboard.MainMenuKeyboard;
import com.gbyzzz.linkedinjobsbot.controller.command.keyboard.WorkplaceKeyboard;
import com.gbyzzz.linkedinjobsbot.entity.SearchParams;
import com.gbyzzz.linkedinjobsbot.entity.UserProfile;
import com.gbyzzz.linkedinjobsbot.service.RedisService;
import com.gbyzzz.linkedinjobsbot.service.SearchParamsService;
import com.gbyzzz.linkedinjobsbot.service.UserProfileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.gbyzzz.linkedinjobsbot.controller.command.keyboard.WorkplaceKeyboard.getWorkplaceCallbackAction;
import static com.gbyzzz.linkedinjobsbot.controller.command.keyboard.WorkplaceKeyboard.getWorkplaceValue;

@Component("ADD_WORKPLACE")
@AllArgsConstructor
public class AddWorkplaceCommand implements Command {

    private final UserProfileService userProfileService;
    private final WorkplaceKeyboard workplaceKeyboard;
    private final RedisService redisService;

    private static final String REPLY = "Enter keywords that should be included(separate them by space)";
    private static final String REPLY_NEXT = "Enter keywords that should be included(separate them by space)";

    @Override
    public SendMessage execute(Update update) {
        SendMessage sendMessage;
        Long id = update.getCallbackQuery().getMessage().getChatId();
        String data = update.getCallbackQuery().getData();
        if (data.equals("next")) {
            UserProfile userProfile = userProfileService.getUserProfileById(id).get();
            userProfile.setBotState(UserProfile.BotState.ADD_FILTER_INCLUDE);
            userProfileService.save(userProfile);
            SearchParams searchParams = (SearchParams) redisService.getFromTempRepository(id);
            searchParams.getSearchFilters().put("workplaceType", getWorkplaceValue());
            sendMessage = new SendMessage(id.toString(), REPLY);
        } else {
            getWorkplaceCallbackAction(data);
            sendMessage = new SendMessage(id.toString(), REPLY_NEXT);
            sendMessage.setReplyMarkup(workplaceKeyboard.getReplyButtons());

        }
        return sendMessage;
    }
}

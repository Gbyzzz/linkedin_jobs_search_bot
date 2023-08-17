package com.gbyzzz.linkedinjobsbot.controller.command.impl.search;

import com.gbyzzz.linkedinjobsbot.controller.MessageText;
import com.gbyzzz.linkedinjobsbot.controller.command.Command;
import com.gbyzzz.linkedinjobsbot.controller.command.keyboard.AfterAddingSearchKeyboard;
import com.gbyzzz.linkedinjobsbot.controller.command.keyboard.JobTypeKeyboard;
import com.gbyzzz.linkedinjobsbot.controller.command.keyboard.MainMenuKeyboard;
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

import static com.gbyzzz.linkedinjobsbot.controller.command.keyboard.WorkplaceKeyboard.getWorkplaceCallbackAction;
import static com.gbyzzz.linkedinjobsbot.controller.command.keyboard.WorkplaceKeyboard.getWorkplaceValue;

@Component("ADD_WORKPLACE")
@AllArgsConstructor
public class AddWorkplaceCommand implements Command {

    private final UserProfileService userProfileService;
    private final WorkplaceKeyboard workplaceKeyboard;
    private final RedisService redisService;

    @Override
    public Reply execute(Update update) {
        SendMessage sendMessage;
        Long id = update.getCallbackQuery().getMessage().getChatId();
        String data = update.getCallbackQuery().getData();
        Reply reply;
        if (data.equals(MessageText.NEXT.getValue())) {
            UserProfile userProfile = userProfileService.getUserProfileById(id).get();
            userProfile.setBotState(UserProfile.BotState.ADD_FILTER_INCLUDE);
            userProfileService.save(userProfile);
            String workplaceType = getWorkplaceValue();
            if(!workplaceType.isEmpty()) {
                SearchParams searchParams = redisService.getFromTempRepository(id);
                searchParams.getSearchFilters().put(MessageText.WORKPLACE_TYPE.getValue(),
                        workplaceType);
                redisService.saveToTempRepository(searchParams, id);
            }
            reply = new Reply(new SendMessage(id.toString(),
                    MessageText.ADD_WORKPLACE_REPLY.getValue()), false);
        } else {
            getWorkplaceCallbackAction(data);
            sendMessage = new SendMessage(id.toString(),
                    MessageText.ADD_WORKPLACE_REPLY.getValue());
            sendMessage.setReplyMarkup(workplaceKeyboard.getReplyButtons());
            reply = new Reply(sendMessage, true);
        }
        return reply;
    }
}

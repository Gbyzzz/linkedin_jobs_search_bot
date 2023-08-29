package com.gbyzzz.linkedinjobsbot.controller.command.impl.search;

import com.gbyzzz.linkedinjobsbot.controller.MessageText;
import com.gbyzzz.linkedinjobsbot.controller.command.Command;
import com.gbyzzz.linkedinjobsbot.controller.command.keyboard.WorkplaceKeyboard;
import com.gbyzzz.linkedinjobsbot.dto.Reply;
import com.gbyzzz.linkedinjobsbot.entity.FilterParams;
import com.gbyzzz.linkedinjobsbot.entity.SearchParams;
import com.gbyzzz.linkedinjobsbot.entity.UserProfile;
import com.gbyzzz.linkedinjobsbot.service.RedisService;
import com.gbyzzz.linkedinjobsbot.service.UserProfileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.gbyzzz.linkedinjobsbot.controller.command.keyboard.WorkplaceKeyboard.getWorkplaceCallbackAction;
import static com.gbyzzz.linkedinjobsbot.controller.command.keyboard.WorkplaceKeyboard.getWorkplaceValue;

@Component(MessageText.ADD_WORKPLACE)
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
        SearchParams searchParams = redisService.getFromTempRepository(id);
        if (data.equals(MessageText.NEXT)) {
            UserProfile userProfile = userProfileService.getUserProfileById(id).get();
            userProfile.setBotState(UserProfile.BotState.ADD_FILTER_INCLUDE);
            userProfileService.save(userProfile);
            String workplaceType = getWorkplaceValue();
            if (searchParams.getFilterParams() == null) {
                searchParams.setFilterParams(new FilterParams());
            }
            if (!workplaceType.isEmpty()) {
                searchParams.getSearchFilters().put(MessageText.WORKPLACE_TYPE, workplaceType);
                redisService.saveToTempRepository(searchParams, id);
            }
            StringBuilder stringBuilder = new StringBuilder();
            if (searchParams.getFilterParams().getIncludeWordsInDescription() != null) {
                stringBuilder.append(MessageText.INCLUDE_EDIT_START);
                for (String include : searchParams.getFilterParams().getIncludeWordsInDescription()) {
                    stringBuilder.append(include).append(MessageText.SPACE);
                }
                stringBuilder.append(MessageText.NEW_LINE).append(MessageText.NEW_LINE)
                        .append(MessageText.ADD_WORKPLACE_REPLY);
                stringBuilder.append(MessageText.NEW_LINE).append(MessageText.TEXT_INPUT_EDIT_END)
                        .append(MessageText.CANCEL_EDITING_COMMAND);
            } else {
                stringBuilder.append(MessageText.ADD_WORKPLACE_REPLY);
            }
            reply = new Reply(new SendMessage(id.toString(),
                    stringBuilder.toString()), false);
        } else {
            getWorkplaceCallbackAction(data);
            StringBuilder stringBuilder = new StringBuilder();
            if(searchParams.getId() != null) {
                stringBuilder.append(MessageText.CANCEL_EDITING_COMMAND)
                        .append(MessageText.ADD_WORKPLACE_REPLY);
            } else {
                stringBuilder.append(MessageText.ADD_WORKPLACE_REPLY);
            }
            sendMessage = new SendMessage(id.toString(), stringBuilder.toString());
            sendMessage.setReplyMarkup(workplaceKeyboard.getReplyButtons());
            reply = new Reply(sendMessage, true);
        }
        return reply;
    }
}

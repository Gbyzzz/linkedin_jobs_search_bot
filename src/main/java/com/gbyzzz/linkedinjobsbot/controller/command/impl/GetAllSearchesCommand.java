package com.gbyzzz.linkedinjobsbot.controller.command.impl;

import com.gbyzzz.linkedinjobsbot.controller.MessageText;
import com.gbyzzz.linkedinjobsbot.controller.command.Command;
import com.gbyzzz.linkedinjobsbot.controller.command.keyboard.PaginationKeyboard;
import com.gbyzzz.linkedinjobsbot.dto.Reply;
import com.gbyzzz.linkedinjobsbot.entity.SearchParams;
import com.gbyzzz.linkedinjobsbot.entity.UserProfile;
import com.gbyzzz.linkedinjobsbot.service.SearchParamsService;
import com.gbyzzz.linkedinjobsbot.service.UserProfileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.util.List;

@Component(MessageText.GET_ALL_SEARCHES)
@AllArgsConstructor
public class GetAllSearchesCommand implements Command {

    private final SearchParamsService searchParamsService;
    private final PaginationKeyboard paginationKeyboard;
    private final UserProfileService userProfileService;

    @Override
    public Reply execute(Update update) throws IOException {
        Long id = update.getMessage().getChatId();
        SendMessage sendMessage;
        List<SearchParams> searchParams = searchParamsService.findAllByUserId(id);

        if (!searchParams.isEmpty()) {
            sendMessage = new SendMessage(searchParams.get(0).getUserProfile()
                    .getChatId().toString(),
                    MessageText.makeSearchReply(0, searchParams));

            sendMessage.setReplyMarkup(paginationKeyboard.getReplyButtons(0,
                    searchParams.size(), UserProfile.BotState.SEARCHES.name(),
                    MessageText.ZERO));
        } else {
            sendMessage = new SendMessage(id.toString(),
                    MessageText.GET_ALL_SEARCHES_REPLY);
        }
        UserProfile userProfile = userProfileService.getUserProfileById(id).get();
        userProfile.setBotState(UserProfile.BotState.SEARCHES);
        userProfileService.save(userProfile);
        return new Reply(sendMessage, false);
    }
}

package com.gbyzzz.linkedinjobsbot.controller.command.impl.filter;

import com.gbyzzz.linkedinjobsbot.controller.MessageText;
import com.gbyzzz.linkedinjobsbot.controller.command.Command;
import com.gbyzzz.linkedinjobsbot.controller.command.impl.MakeFirstSearchCommand;
import com.gbyzzz.linkedinjobsbot.dto.Reply;
import com.gbyzzz.linkedinjobsbot.entity.SearchParams;
import com.gbyzzz.linkedinjobsbot.entity.UserProfile;
import com.gbyzzz.linkedinjobsbot.service.RedisService;
import com.gbyzzz.linkedinjobsbot.service.UserProfileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;


@Component(MessageText.ADD_FILTER_EXCLUDE)
@AllArgsConstructor
public class AddFilterExcludeCommand implements Command {

    private final UserProfileService userProfileService;
    private final RedisService redisService;
    private final MakeFirstSearchCommand makeFirstSearchCommand;

    @Override
    public Reply execute(Update update) throws IOException {
        Long id = update.getMessage().getChatId();
        String [] keywords = update.getMessage().getText().split(MessageText.SPACE);
        UserProfile userProfile = userProfileService.getUserProfileById(update.getMessage()
                .getChatId()).get();
        SearchParams searchParams = redisService.getFromTempRepository(id);
        searchParams.getFilterParams().setExcludeWordsFromTitle(keywords);
        redisService.saveToTempRepository(searchParams, id);
        userProfile.setBotState(UserProfile.BotState.NEW);
        userProfileService.save(userProfile);

        return new Reply(new SendMessage(id.toString(),
                MessageText.ADD_FILTER_EXCLUDE_REPLY), false);
    }
}

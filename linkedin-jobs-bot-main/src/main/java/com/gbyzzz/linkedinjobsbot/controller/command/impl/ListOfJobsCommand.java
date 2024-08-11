package com.gbyzzz.linkedinjobsbot.controller.command.impl;

import com.gbyzzz.linkedinjobsbot.controller.MessageText;
import com.gbyzzz.linkedinjobsbot.controller.command.Command;
import com.gbyzzz.linkedinjobsbot.controller.command.impl.search.AddSearchCommand;
import com.gbyzzz.linkedinjobsbot.controller.command.keyboard.PaginationKeyboard;
import com.gbyzzz.linkedinjobsbot.dto.Reply;
import com.gbyzzz.linkedinjobsbot.entity.SavedJob;
import com.gbyzzz.linkedinjobsbot.entity.SearchParams;
import com.gbyzzz.linkedinjobsbot.entity.UserProfile;
import com.gbyzzz.linkedinjobsbot.service.MessageService;
import com.gbyzzz.linkedinjobsbot.service.RedisService;
import com.gbyzzz.linkedinjobsbot.service.SavedJobService;
import com.gbyzzz.linkedinjobsbot.service.SearchParamsService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component(MessageText.WATCH_LIST_OF_JOBS)
@AllArgsConstructor
public class ListOfJobsCommand implements Command {

    private final SearchParamsService searchParamsService;
    private final AddSearchCommand addSearchCommand;
    private final RedisService redisService;
    private final MessageService messageService;

    @Override
    public Reply execute(Update update) throws IOException {
        Long id = update.getCallbackQuery().getMessage().getChatId();
        String[] command = update.getCallbackQuery().getData()
                .split(MessageText.BUTTON_VALUE_SEPARATOR);
        SendMessage sendMessage = null;

        switch (command[0]) {
            case MessageText.NEW -> sendMessage = messageService.getNewJobByUserId(id, command);
            case MessageText.APPLIED -> sendMessage = messageService.getAppliedJobByUserId(id, command);
            case MessageText.SEARCHES -> {
                if(command[1].equals(MessageText.EDIT)){
                    redisService.saveToTempRepository(searchParamsService.findById(Long.parseLong(command[4])),
                            update.getCallbackQuery().getMessage().getChatId());
                    return addSearchCommand.execute(update);
                }
                sendMessage = messageService.getSearchByUserId(id, command);
            }

        }
        return new Reply(sendMessage, true);
    }
}

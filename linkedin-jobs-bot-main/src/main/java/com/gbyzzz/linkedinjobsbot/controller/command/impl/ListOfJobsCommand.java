package com.gbyzzz.linkedinjobsbot.controller.command.impl;


import com.gbyzzz.linkedinjobsbot.controller.command.Command;
import com.gbyzzz.linkedinjobsbot.controller.command.impl.search.AddSearchCommand;
import com.gbyzzz.linkedinjobsbot.dto.Reply;
import com.gbyzzz.linkedinjobsbot.modules.commons.values.MessageText;
import com.gbyzzz.linkedinjobsbot.modules.postgresdb.service.SearchParamsService;
import com.gbyzzz.linkedinjobsbot.modules.redisdb.service.RedisService;
import com.gbyzzz.linkedinjobsbot.service.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;

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
        String[] command = update.getCallbackQuery().getData().split(MessageText.BUTTON_VALUE_SEPARATOR);
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

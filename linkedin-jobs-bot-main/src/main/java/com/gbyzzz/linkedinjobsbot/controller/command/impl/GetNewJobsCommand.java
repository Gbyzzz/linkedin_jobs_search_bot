package com.gbyzzz.linkedinjobsbot.controller.command.impl;

import com.gbyzzz.linkedinjobsbot.controller.command.Command;
import com.gbyzzz.linkedinjobsbot.dto.Reply;
import com.gbyzzz.linkedinjobsbot.modules.commons.values.MessageText;
import com.gbyzzz.linkedinjobsbot.service.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;

@Component(MessageText.GET_NEW_JOBS)
@AllArgsConstructor
public class GetNewJobsCommand implements Command {

    private final MessageService messageService;

    @Override
    public Reply execute(Update update) throws IOException {
        return new Reply(messageService.getNewJobByUserId(update.getMessage().getChatId(),
                new String[]{MessageText.NEW, MessageText.FIRST, MessageText.ALL}), false);
    }
}

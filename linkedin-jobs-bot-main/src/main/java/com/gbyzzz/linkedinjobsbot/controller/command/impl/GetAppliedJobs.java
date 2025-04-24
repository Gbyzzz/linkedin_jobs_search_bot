package com.gbyzzz.linkedinjobsbot.controller.command.impl;

import com.gbyzzz.linkedinjobsbot.controller.command.Command;
import com.gbyzzz.linkedinjobsbot.dto.Reply;
import com.gbyzzz.linkedinjobsbot.modules.commons.values.MessageText;
import com.gbyzzz.linkedinjobsbot.service.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;

@Component(MessageText.GET_APPLIED_JOBS)
@AllArgsConstructor
public class GetAppliedJobs implements Command {

    private final MessageService messageService;


    @Override
    public Reply execute(Update update) throws IOException {
        return new Reply(messageService.getAppliedJobByUserId(update.getMessage().getChatId(),
                new String[]{MessageText.APPLIED, MessageText.FIRST, MessageText.ALL}), false);
    }
}

package com.gbyzzz.linkedinjobsbot.controller.command.impl;

import com.gbyzzz.linkedinjobsbot.controller.command.Command;
import com.gbyzzz.linkedinjobsbot.dto.Reply;
import com.gbyzzz.linkedinjobsbot.modules.commons.values.MessageText;
import com.gbyzzz.linkedinjobsbot.modules.redisdb.service.RedisService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;

@Component(MessageText.CANCEL_EDITING)
@AllArgsConstructor
public class CancelEditingCommand implements Command {

    private final RedisService redisService;
    private final GetAllSearchesCommand getAllSearchesCommand;


    @Override
    public Reply execute(Update update) throws IOException {
        redisService.deleteFromTempRepository(update.getMessage().getChatId());
        return getAllSearchesCommand.execute(update);
    }
}

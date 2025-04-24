package com.gbyzzz.linkedinjobsbot.controller.command.impl;

import com.gbyzzz.linkedinjobsbot.controller.command.Command;
import com.gbyzzz.linkedinjobsbot.dto.Reply;
import com.gbyzzz.linkedinjobsbot.modules.commons.values.MessageText;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;

@Component(MessageText.NO_ACCOUNT)
public class NoAccountCommand implements Command {


    @Override
    public Reply execute(Update update) throws IOException {
        return new Reply(new SendMessage(update.getMessage().getChatId().toString(),
                MessageText.NO_ACCOUNT_REPLY),false);
    }
}

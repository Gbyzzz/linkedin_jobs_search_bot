package com.gbyzzz.linkedinjobsbot.controller.command.impl;

import com.gbyzzz.linkedinjobsbot.controller.command.Command;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component("EXIT")
@AllArgsConstructor
public class ExitCommand implements Command {
    @Override
    public SendMessage execute(Update update) {
        return new SendMessage(update.getMessage().getChatId().toString(), "Exit");
    }
}

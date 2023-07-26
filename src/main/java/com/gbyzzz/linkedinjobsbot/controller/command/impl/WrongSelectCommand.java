package com.gbyzzz.linkedinjobsbot.controller.command.impl;

import com.gbyzzz.linkedinjobsbot.controller.command.Command;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component("WRONG")
public class WrongSelectCommand implements Command {
    @Override
    public SendMessage execute(Update update) {
        String reply = "WrongSelect";
        return new SendMessage(update.getMessage().getChatId().toString(), "Exit");
    }
}

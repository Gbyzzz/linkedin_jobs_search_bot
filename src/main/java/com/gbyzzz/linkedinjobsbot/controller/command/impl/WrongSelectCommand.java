package com.gbyzzz.linkedinjobsbot.controller.command.impl;

import com.gbyzzz.linkedinjobsbot.controller.command.Command;
import com.gbyzzz.linkedinjobsbot.dto.Reply;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component("WRONG")
public class WrongSelectCommand implements Command {
    @Override
    public Reply execute(Update update) {
        String reply = "WrongSelect";
        return new Reply(new SendMessage(update.getMessage().getChatId().toString(), "Exit"),
                false);
    }
}

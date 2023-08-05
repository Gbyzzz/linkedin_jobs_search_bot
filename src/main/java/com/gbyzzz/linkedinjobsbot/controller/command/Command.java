package com.gbyzzz.linkedinjobsbot.controller.command;

import com.gbyzzz.linkedinjobsbot.dto.Reply;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;

public interface Command {
    Reply execute(Update update) throws IOException;
}

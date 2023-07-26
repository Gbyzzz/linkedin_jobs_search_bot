package com.gbyzzz.linkedinjobsbot.controller.command;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;

public interface Command {
    SendMessage execute(Update update) throws IOException;
}

package com.gbyzzz.linkedinjobsbot.controller.command;

import com.gbyzzz.linkedinjobsbot.dto.Reply;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;

public interface Command {
    Reply execute(Update update) throws IOException;
}

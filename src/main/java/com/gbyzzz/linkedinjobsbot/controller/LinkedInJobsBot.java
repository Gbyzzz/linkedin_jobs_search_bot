package com.gbyzzz.linkedinjobsbot.controller;

import com.gbyzzz.linkedinjobsbot.controller.command.Command;
import com.gbyzzz.linkedinjobsbot.dto.Reply;
import com.gbyzzz.linkedinjobsbot.entity.converter.SendToEditMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

@Component
public class LinkedInJobsBot extends TelegramLongPollingBot {

    private final CommandProvider provider;
    private final SendToEditMessageConverter converter;

    public LinkedInJobsBot(@Value("${bot.token}") String botToken,
                           CommandProvider provider, SendToEditMessageConverter converter) {
        super(botToken);
        this.provider = provider;
        this.converter = converter;
    }

    @Override
    public void onUpdateReceived(Update update) {
        long start = System.currentTimeMillis();
        if (update.hasCallbackQuery() || update.hasMessage()) {
            Command command = provider.getCommand(update);
            Reply reply;
            try {
                reply = command.execute(update);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            long end = System.currentTimeMillis();
            System.out.println(end-start);
            if(!reply.isUpdate()) {
                sendMessage(reply.getSendMessage());
            } else {
                sendMessage(converter.convert(reply.getSendMessage(), update));
            }
        }

    }

    @Override
    public String getBotUsername() {
        return "LinkedIn_Job_Search_Bot";
    }

    public void sendMessage(EditMessageText editMessageText) {
        try {
            execute(editMessageText);
        } catch (TelegramApiException e) {
            System.out.println(MessageText.ERROR + e);
        }
    }
    public void sendMessage(SendMessage message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            System.out.println(MessageText.ERROR + e);
        }
    }
}

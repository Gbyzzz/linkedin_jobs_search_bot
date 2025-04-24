package com.gbyzzz.linkedinjobsbot.controller;

import com.gbyzzz.linkedinjobsbot.controller.command.Command;
import com.gbyzzz.linkedinjobsbot.dto.Reply;
import com.gbyzzz.linkedinjobsbot.dto.converter.SendToEditMessageConverter;
import com.gbyzzz.linkedinjobsbot.modules.commons.values.MessageText;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.io.IOException;

@Component
public class LinkedInJobsBot implements SpringLongPollingBot, LongPollingSingleThreadUpdateConsumer {
    @Value("${bot.token}")
    private String botToken;

    private final CommandProvider provider;
    private final SendToEditMessageConverter converter;
    private final TelegramClient telegramClient;

    public LinkedInJobsBot(@Value("${bot.token}") String botToken,
                           CommandProvider provider, SendToEditMessageConverter converter) {
        telegramClient = new OkHttpTelegramClient(botToken);
        this.provider = provider;
        this.converter = converter;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public LongPollingUpdateConsumer getUpdatesConsumer() {
        return this;
    }
//    @Override
//    public void onUpdateReceived(Update update) {
//        long start = System.currentTimeMillis();
//        if ((update.getCallbackQuery() != null &&
//                !update.getCallbackQuery().getData().split("_")[0].equals(MessageText.NA)) ||
//                update.hasMessage()) {
//            Command command = provider.getCommand(update);
//            Reply reply;
//            try {
//                reply = command.execute(update);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//            long end = System.currentTimeMillis();
//            System.out.println(end-start);
//            if(!reply.isUpdate()) {
//                sendMessage(reply.getSendMessage());
//            } else {
//                sendMessage(converter.convert(reply.getSendMessage(), update));
//            }
//        }
//

//    }
//    @Override
//    public String getBotUsername() {
//        return "LinkedIn_Job_Search_Bot";

//    }
    public void sendMessage(EditMessageText editMessageText) {
        try {
            telegramClient.execute(editMessageText);
        } catch (TelegramApiException e) {
            System.out.println(MessageText.ERROR + e);
        }
    }

    public void sendMessage(SendMessage message) {
        try {
            telegramClient.execute(message);
        } catch (TelegramApiException e) {
            System.out.println(MessageText.ERROR + e);
        }
    }

    @Override
    public void consume(Update update) {
        long start = System.currentTimeMillis();
        if ((update.getCallbackQuery() != null &&
                !update.getCallbackQuery().getData().split("_")[0].equals(MessageText.NA)) ||
                update.hasMessage()) {
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
}

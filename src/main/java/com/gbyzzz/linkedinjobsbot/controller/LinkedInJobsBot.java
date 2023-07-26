package com.gbyzzz.linkedinjobsbot.controller;

import com.gbyzzz.linkedinjobsbot.controller.command.Command;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

@Component
public class LinkedInJobsBot extends TelegramLongPollingBot {

    private final CommandProvider provider;

    public LinkedInJobsBot(@Value("${bot.token}") String botToken, CommandProvider provider) {
        super(botToken);
        this.provider = provider;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasCallbackQuery() || update.hasMessage()) {
            Command command = provider.getCommand(update);
            SendMessage message = null;
            try {
                message = command.execute(update);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if(!update.hasCallbackQuery() || update.getCallbackQuery().getData().equals("next")) {
                sendMessage(message);
            } else {
                sendMessage(new EditMessageText(message.getChatId(), update.getCallbackQuery()
                        .getMessage().getMessageId(), update.getCallbackQuery().getInlineMessageId(),
                        message.getText(), message.getParseMode(), message.getDisableWebPagePreview(),
                        (InlineKeyboardMarkup) message.getReplyMarkup(), message.getEntities()));
            }
        }

    }

    @Override
    public String getBotUsername() {
        return "LinkedIn_Job_Search_Bot";
    }

    private void sendMessage(EditMessageText editMessageText) {
        try {
            execute(editMessageText);
        } catch (TelegramApiException e) {
            System.out.println("Ошибка отправки сообщения: " + e);
        }
    }
    private void sendMessage(SendMessage message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            System.out.println("Ошибка отправки сообщения: " + e);
        }
    }
}

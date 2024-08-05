package com.gbyzzz.linkedinjobsbot.entity.converter;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@Service
public class SendToEditMessageConverter {
    public EditMessageText convert (SendMessage message, Update update) {
        return EditMessageText.builder()
                .chatId(message.getChatId())
                .messageId(update.getCallbackQuery().getMessage().getMessageId())
                .inlineMessageId(update.getCallbackQuery().getInlineMessageId())
                .replyMarkup((InlineKeyboardMarkup)message.getReplyMarkup())
                .text(message.getText())
                .parseMode(message.getParseMode())
                .disableWebPagePreview(message.getDisableWebPagePreview())
                .entities(message.getEntities())
                .linkPreviewOptions(message.getLinkPreviewOptions())
                .build();
    }
}

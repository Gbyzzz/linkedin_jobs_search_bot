package com.gbyzzz.linkedinjobsbot.entity.converter;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@Service
public class SendToEditMessageConverter {
    public EditMessageText convert (SendMessage message, Update update) {
        return new EditMessageText(message.getChatId(), update.getCallbackQuery()
                .getMessage().getMessageId(), update.getCallbackQuery().getInlineMessageId(),
                message.getText(), message.getParseMode(), message.getDisableWebPagePreview(),
                (InlineKeyboardMarkup) message.getReplyMarkup(), message.getEntities());
    }
}

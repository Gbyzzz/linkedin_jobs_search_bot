package com.gbyzzz.linkedinjobsbot.controller.command.keyboard;

import com.gbyzzz.linkedinjobsbot.controller.MessageText;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
public class LocationKeyboard {

    public InlineKeyboardMarkup getReplyButtons() {
        InlineKeyboardButton israelButton = new InlineKeyboardButton(MessageText.BUTTON_ISRAEL);
        israelButton.setCallbackData(MessageText.BUTTON_ISRAEL);

        InlineKeyboardButton telAvivButton = new InlineKeyboardButton(MessageText.BUTTON_TEL_AVIV);
        telAvivButton.setCallbackData(MessageText.BUTTON_TEL_AVIV_VALUE);

        InlineKeyboardButton haifaButton = new InlineKeyboardButton(MessageText.BUTTON_HAIFA);
        haifaButton.setCallbackData(MessageText.BUTTON_HAIFA);

        InlineKeyboardButton jerusalemButton = new InlineKeyboardButton(MessageText.BUTTON_JERUSALEM);
        jerusalemButton.setCallbackData(MessageText.BUTTON_JERUSALEM);

        InlineKeyboardButton usaButton = new InlineKeyboardButton(MessageText.BUTTON_USA);
        usaButton.setCallbackData(MessageText.BUTTON_USA);

        InlineKeyboardRow row1 = new InlineKeyboardRow();
        row1.add(israelButton);
        row1.add(telAvivButton);

        InlineKeyboardRow row2 = new InlineKeyboardRow();
        row2.add(haifaButton);
        row2.add(jerusalemButton);

        InlineKeyboardRow row3 = new InlineKeyboardRow();
        row3.add(usaButton);

        List<InlineKeyboardRow> rows = new ArrayList<>();
        rows.add(row1);
        rows.add(row2);
        rows.add(row3);

        return new InlineKeyboardMarkup(rows);
    }
}

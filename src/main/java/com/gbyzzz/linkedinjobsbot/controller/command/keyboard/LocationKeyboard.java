package com.gbyzzz.linkedinjobsbot.controller.command.keyboard;

import com.gbyzzz.linkedinjobsbot.controller.MessageText;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class LocationKeyboard {

    public InlineKeyboardMarkup getReplyButtons() {
        InlineKeyboardButton israelButton = new InlineKeyboardButton();
        israelButton.setText(MessageText.BUTTON_ISRAEL);
        israelButton.setCallbackData(MessageText.BUTTON_ISRAEL);

        InlineKeyboardButton telAvivButton = new InlineKeyboardButton();
        telAvivButton.setText(MessageText.BUTTON_TEL_AVIV);
        telAvivButton.setCallbackData(MessageText.BUTTON_TEL_AVIV_VALUE);

        InlineKeyboardButton haifaButton = new InlineKeyboardButton();
        haifaButton.setText(MessageText.BUTTON_HAIFA);
        haifaButton.setCallbackData(MessageText.BUTTON_HAIFA);

        InlineKeyboardButton jerusalemButton = new InlineKeyboardButton();
        jerusalemButton.setText(MessageText.BUTTON_JERUSALEM);
        jerusalemButton.setCallbackData(MessageText.BUTTON_JERUSALEM);

        InlineKeyboardButton usaButton = new InlineKeyboardButton();
        usaButton.setText(MessageText.BUTTON_USA);
        usaButton.setCallbackData(MessageText.BUTTON_USA);

        List<InlineKeyboardButton> row1 = new ArrayList<>();
        row1.add(israelButton);
        row1.add(telAvivButton);

        List<InlineKeyboardButton> row2 = new ArrayList<>();
        row2.add(haifaButton);
        row2.add(jerusalemButton);

        List<InlineKeyboardButton> row3 = new ArrayList<>();
        row3.add(usaButton);

        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        rows.add(row1);
        rows.add(row2);
        rows.add(row3);

        return new InlineKeyboardMarkup(rows);
    }
}

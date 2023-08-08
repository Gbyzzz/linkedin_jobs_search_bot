package com.gbyzzz.linkedinjobsbot.controller.command.keyboard;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class LocationKeyboard {

    public InlineKeyboardMarkup getReplyButtons() {
        InlineKeyboardButton israelButton = new InlineKeyboardButton();
        israelButton.setText("Israel");
        israelButton.setCallbackData("Israel");

        InlineKeyboardButton telAvivButton = new InlineKeyboardButton();
        telAvivButton.setText("Tel-Aviv");
        telAvivButton.setCallbackData("Tel_Aviv");

        InlineKeyboardButton haifaButton = new InlineKeyboardButton();
        haifaButton.setText("Haifa");
        haifaButton.setCallbackData("Haifa");

        InlineKeyboardButton jerusalemButton = new InlineKeyboardButton();
        jerusalemButton.setText("Jerusalem");
        jerusalemButton.setCallbackData("Jerusalem");

        InlineKeyboardButton usaButton = new InlineKeyboardButton();
        usaButton.setText("USA");
        usaButton.setCallbackData("USA");

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

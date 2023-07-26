package com.gbyzzz.linkedinjobsbot.controller.command.keyboard;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
public class AfterAddingSearchKeyboard {

    public ReplyKeyboardMarkup getReplyButtons() {
        KeyboardButton addFilters = new KeyboardButton("/add_filters");
        KeyboardButton makeSearch = new KeyboardButton("/make_search");


        KeyboardRow row1 = new KeyboardRow();
        row1.add(addFilters);
        row1.add(makeSearch);
        List<KeyboardRow> keyboardButtonsRow = new ArrayList<>();
        keyboardButtonsRow.add(row1);
        return new ReplyKeyboardMarkup(keyboardButtonsRow);
    }
}

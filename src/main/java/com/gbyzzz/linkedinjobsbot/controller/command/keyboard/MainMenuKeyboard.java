package com.gbyzzz.linkedinjobsbot.controller.command.keyboard;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
public class MainMenuKeyboard {
    public ReplyKeyboardMarkup getReplyButtons() {
        KeyboardButton addSearch = new KeyboardButton("/add_search");
        KeyboardButton getAllSearches = new KeyboardButton("/get_all_searches");
        KeyboardButton getMyJobs = new KeyboardButton("/get_applied_jobs");
        KeyboardButton getNewJobs = new KeyboardButton("/get_new_jobs");


        KeyboardRow row1 = new KeyboardRow();
        row1.add(getNewJobs);
        row1.add(getMyJobs);
        KeyboardRow row2 = new KeyboardRow();
        row2.add(getAllSearches);
        row2.add(addSearch);
        List<KeyboardRow> keyboardButtonsRow = new ArrayList<>();
        keyboardButtonsRow.add(row1);
        keyboardButtonsRow.add(row2);
        return new ReplyKeyboardMarkup(keyboardButtonsRow);
    }
}

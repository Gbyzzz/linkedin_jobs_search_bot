package com.gbyzzz.linkedinjobsbot.controller.command.keyboard;

import com.gbyzzz.linkedinjobsbot.controller.MessageText;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
public class MainMenuKeyboard {
    public ReplyKeyboardMarkup getReplyButtons() {
        KeyboardButton addSearch = new KeyboardButton(MessageText.BUTTON_ADD_SEARCH);
        KeyboardButton getAllSearches = new KeyboardButton(MessageText.BUTTON_GET_ALL_SEARCHES);
        KeyboardButton getMyJobs = new KeyboardButton(MessageText.BUTTON_GET_APPLIED_JOBS);
        KeyboardButton getNewJobs = new KeyboardButton(MessageText.BUTTON_GET_NEW_JOBS);
        KeyboardButton mainMenu = new KeyboardButton(MessageText.BUTTON_MAIN_MENU);


        KeyboardRow row1 = new KeyboardRow();
        row1.add(getNewJobs);
        row1.add(getMyJobs);
        KeyboardRow row2 = new KeyboardRow();
        row2.add(getAllSearches);
        row2.add(addSearch);
        KeyboardRow row3 = new KeyboardRow();
        row3.add(mainMenu);
        List<KeyboardRow> keyboardButtonsRow = new ArrayList<>();
        keyboardButtonsRow.add(row1);
        keyboardButtonsRow.add(row2);
        keyboardButtonsRow.add(row3);
        return new ReplyKeyboardMarkup(keyboardButtonsRow);
    }
}

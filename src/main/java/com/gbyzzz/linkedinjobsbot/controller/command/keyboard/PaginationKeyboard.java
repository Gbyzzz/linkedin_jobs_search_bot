package com.gbyzzz.linkedinjobsbot.controller.command.keyboard;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class PaginationKeyboard {

    public InlineKeyboardMarkup getReplyButtons(int index, int size) {

        InlineKeyboardButton appliedButton = new InlineKeyboardButton();
        appliedButton.setText("✅ Applied");
        appliedButton.setCallbackData("apply_" + index);


        List<InlineKeyboardButton> row1 = new ArrayList<>();

        if (index > 0) {
            InlineKeyboardButton previousButton = new InlineKeyboardButton();
            previousButton.setText("⬅\uFE0F Previous");
            previousButton.setCallbackData("previous_" + (index - 1));
            row1.add(previousButton);
        }

        row1.add(appliedButton);

        if (index + 1 < size) {
            InlineKeyboardButton nextButton = new InlineKeyboardButton();
            nextButton.setText("➡\uFE0F Next");
            nextButton.setCallbackData("next_" + (index + 1));
            row1.add(nextButton);
        }
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        rows.add(row1);

        return new InlineKeyboardMarkup(rows);
    }
}

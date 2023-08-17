package com.gbyzzz.linkedinjobsbot.controller.command.keyboard;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class PaginationKeyboard {

    public InlineKeyboardMarkup getReplyButtons(int index, int size, String state,
                                                String searchParamsId) {

        InlineKeyboardButton deleteButton = new InlineKeyboardButton();
        deleteButton.setText("❌ Delete");
        deleteButton.setCallbackData("delete_" + state + "_" + searchParamsId + "_" + index);

        InlineKeyboardButton rejectButton = new InlineKeyboardButton();
        rejectButton.setText("❌ Rejected");
        rejectButton.setCallbackData("rejected_" + state + "_" + searchParamsId + "_" + index);

        InlineKeyboardButton resultsButton = new InlineKeyboardButton();
        resultsButton.setText("\uD83D\uDCCA Results(new)");
        resultsButton.setCallbackData("results_" + state + "_" + searchParamsId + "_" + index);

        InlineKeyboardButton appliedButton = new InlineKeyboardButton();
        appliedButton.setText("✅ Applied");
        appliedButton.setCallbackData("apply_" + state + "_" + searchParamsId + "_" + index);


        List<InlineKeyboardButton> row1 = new ArrayList<>();

        if (index > 0) {
            InlineKeyboardButton previousButton = new InlineKeyboardButton();
            previousButton.setText("⬅\uFE0F Previous");
            previousButton.setCallbackData("previous_" + state + "_" + searchParamsId +
                    "_" + (index - 1));
            row1.add(previousButton);
        }



        switch (state){
            case "NEW" -> {
                row1.add(deleteButton);
                row1.add(appliedButton);
            }
            case "SEARCHES" -> {
                row1.add(deleteButton);
                row1.add(resultsButton);
            }
            case "APPLIED" -> row1.add(rejectButton);
        }

        if (index + 1 < size) {
            InlineKeyboardButton nextButton = new InlineKeyboardButton();
            nextButton.setText("➡\uFE0F Next");
            nextButton.setCallbackData("next_" + state + "_" + searchParamsId + "_" + (index + 1));
            row1.add(nextButton);
        }
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        rows.add(row1);

        return new InlineKeyboardMarkup(rows);
    }
}

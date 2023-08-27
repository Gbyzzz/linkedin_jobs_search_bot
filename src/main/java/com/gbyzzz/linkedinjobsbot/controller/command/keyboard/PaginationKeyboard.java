package com.gbyzzz.linkedinjobsbot.controller.command.keyboard;

import com.gbyzzz.linkedinjobsbot.controller.MessageText;
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
        deleteButton.setText(MessageText.BUTTON_NO_CHECK_BOX + MessageText.SPACE +
                MessageText.BUTTON_DELETE);
        deleteButton.setCallbackData(MessageText.DELETE + MessageText.BUTTON_VALUE_SEPARATOR +
                state + MessageText.BUTTON_VALUE_SEPARATOR + searchParamsId +
                MessageText.BUTTON_VALUE_SEPARATOR + index);

        InlineKeyboardButton rejectButton = new InlineKeyboardButton();
        rejectButton.setText(MessageText.BUTTON_NO_CHECK_BOX + MessageText.SPACE +
                MessageText.BUTTON_REJECTED);
        rejectButton.setCallbackData(MessageText.REJECTED + MessageText.BUTTON_VALUE_SEPARATOR +
                state + MessageText.BUTTON_VALUE_SEPARATOR + searchParamsId +
                MessageText.BUTTON_VALUE_SEPARATOR + index);

        InlineKeyboardButton appliedButton = new InlineKeyboardButton();
        appliedButton.setText(MessageText.BUTTON_YES_CHECK_BOX + MessageText.SPACE +
                MessageText.BUTTON_APPLIED);
        appliedButton.setCallbackData(MessageText.APPLY + MessageText.BUTTON_VALUE_SEPARATOR +
                state + MessageText.BUTTON_VALUE_SEPARATOR + searchParamsId +
                MessageText.BUTTON_VALUE_SEPARATOR + index);


        List<InlineKeyboardButton> row1 = new ArrayList<>();

        if (index > 0) {
            InlineKeyboardButton previousButton = new InlineKeyboardButton();
            previousButton.setText(MessageText.BUTTON_PREVIOUS_ARROW + MessageText.SPACE +
                    MessageText.BUTTON_PREVIOUS);
            previousButton.setCallbackData(MessageText.PREVIOUS +
                    MessageText.BUTTON_VALUE_SEPARATOR + state +
                    MessageText.BUTTON_VALUE_SEPARATOR + searchParamsId +
                    MessageText.BUTTON_VALUE_SEPARATOR + (index - 1));
            row1.add(previousButton);
        }



        switch (state){
            case MessageText.NEW -> {
                row1.add(deleteButton);
                row1.add(appliedButton);
            }
            case MessageText.SEARCHES -> {
                InlineKeyboardButton resultsButton = new InlineKeyboardButton();
                resultsButton.setText(MessageText.BUTTON_RESULTS_EMOJI + MessageText.SPACE +
                        MessageText.BUTTON_RESULTS);
                resultsButton.setCallbackData(MessageText.RESULTS + MessageText.BUTTON_VALUE_SEPARATOR +
                        state + MessageText.BUTTON_VALUE_SEPARATOR + searchParamsId +
                        MessageText.BUTTON_VALUE_SEPARATOR + index);

                InlineKeyboardButton editButton = new InlineKeyboardButton();
                editButton.setText(MessageText.BUTTON_EDIT_EMOJI + MessageText.SPACE +
                        MessageText.BUTTON_EDIT);
                editButton.setCallbackData(MessageText.EDIT + MessageText.BUTTON_VALUE_SEPARATOR +
                        state + MessageText.BUTTON_VALUE_SEPARATOR + searchParamsId +
                        MessageText.BUTTON_VALUE_SEPARATOR + index);

                row1.add(deleteButton);
                row1.add(editButton);
                row1.add(resultsButton);
            }
            case MessageText.APPLIED -> row1.add(rejectButton);
        }

        if (index + 1 < size) {
            InlineKeyboardButton nextButton = new InlineKeyboardButton();
            nextButton.setText(MessageText.BUTTON_NEXT_ARROW + MessageText.SPACE +
                    MessageText.BUTTON_NEXT);
            nextButton.setCallbackData(MessageText.NEXT + MessageText.BUTTON_VALUE_SEPARATOR +
                    state + MessageText.BUTTON_VALUE_SEPARATOR + searchParamsId +
                    MessageText.BUTTON_VALUE_SEPARATOR + (index + 1));
            row1.add(nextButton);
        }
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        rows.add(row1);

        return new InlineKeyboardMarkup(rows);
    }
}

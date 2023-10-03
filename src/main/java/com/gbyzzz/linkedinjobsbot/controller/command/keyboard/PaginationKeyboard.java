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
        List<InlineKeyboardButton> row2 = new ArrayList<>();

        InlineKeyboardButton firstButton = new InlineKeyboardButton();
        InlineKeyboardButton previousButton = new InlineKeyboardButton();
        if (index > 0) {
            firstButton.setText(MessageText.BUTTON_FIRST_ARROW + MessageText.SPACE +
                    MessageText.BUTTON_FIRST);
            firstButton.setCallbackData(MessageText.FIRST +
                    MessageText.BUTTON_VALUE_SEPARATOR + state +
                    MessageText.BUTTON_VALUE_SEPARATOR + searchParamsId +
                    MessageText.BUTTON_VALUE_SEPARATOR + 0);
            row2.add(firstButton);

            previousButton = new InlineKeyboardButton();
            previousButton.setText(MessageText.BUTTON_PREVIOUS_ARROW + MessageText.SPACE +
                    MessageText.BUTTON_PREVIOUS);
            previousButton.setCallbackData(MessageText.PREVIOUS +
                    MessageText.BUTTON_VALUE_SEPARATOR + state +
                    MessageText.BUTTON_VALUE_SEPARATOR + searchParamsId +
                    MessageText.BUTTON_VALUE_SEPARATOR + (index - 1));
            row2.add(previousButton);
        } else {
            firstButton.setText(MessageText.BUTTON_DISABLED);
            firstButton.setCallbackData(MessageText.NA);
            row2.add(firstButton);

            previousButton.setText(MessageText.BUTTON_DISABLED);
            previousButton.setCallbackData(MessageText.NA);
            row2.add(previousButton);
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

        InlineKeyboardButton nextButton = new InlineKeyboardButton();
        InlineKeyboardButton lastButton = new InlineKeyboardButton();
        if (index + 1 < size) {
            nextButton.setText(MessageText.BUTTON_NEXT_ARROW + MessageText.SPACE +
                    MessageText.BUTTON_NEXT);
            nextButton.setCallbackData(MessageText.NEXT + MessageText.BUTTON_VALUE_SEPARATOR +
                    state + MessageText.BUTTON_VALUE_SEPARATOR + searchParamsId +
                    MessageText.BUTTON_VALUE_SEPARATOR + (index + 1));
            row2.add(nextButton);

            lastButton = new InlineKeyboardButton();
            lastButton.setText(MessageText.BUTTON_LAST_ARROW + MessageText.SPACE +
                    MessageText.BUTTON_LAST);
            lastButton.setCallbackData(MessageText.LAST + MessageText.BUTTON_VALUE_SEPARATOR +
                    state + MessageText.BUTTON_VALUE_SEPARATOR + searchParamsId +
                    MessageText.BUTTON_VALUE_SEPARATOR + (index + 1));
            row2.add(lastButton);
        } else {
            nextButton.setText(MessageText.BUTTON_DISABLED);
            nextButton.setCallbackData(MessageText.NA);
            row2.add(nextButton);

            lastButton.setText(MessageText.BUTTON_DISABLED);
            lastButton.setCallbackData(MessageText.NA);
            row2.add(lastButton);
        }
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        rows.add(row1);
        rows.add(row2);

        return new InlineKeyboardMarkup(rows);
    }
}

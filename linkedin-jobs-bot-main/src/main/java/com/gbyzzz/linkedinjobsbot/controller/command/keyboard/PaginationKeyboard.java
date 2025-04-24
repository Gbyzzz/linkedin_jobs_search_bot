package com.gbyzzz.linkedinjobsbot.controller.command.keyboard;

import com.gbyzzz.linkedinjobsbot.modules.commons.values.MessageText;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
public class PaginationKeyboard {

    public InlineKeyboardMarkup getReplyButtons( String state,
                                                String searchParamsId, long page, long size, long id) {

            InlineKeyboardButton deleteButton = new InlineKeyboardButton(MessageText.BUTTON_NO_CHECK_BOX + MessageText.SPACE +
                MessageText.BUTTON_DELETE);
        deleteButton.setCallbackData(state + MessageText.BUTTON_VALUE_SEPARATOR + MessageText.DELETE +
                MessageText.BUTTON_VALUE_SEPARATOR + searchParamsId + MessageText.BUTTON_VALUE_SEPARATOR +
                page + MessageText.BUTTON_VALUE_SEPARATOR + id);

        InlineKeyboardButton rejectButton = new InlineKeyboardButton(MessageText.BUTTON_NO_CHECK_BOX +
                MessageText.SPACE + MessageText.BUTTON_REJECTED);
        rejectButton.setCallbackData(state + MessageText.BUTTON_VALUE_SEPARATOR + MessageText.REJECTED +
                MessageText.BUTTON_VALUE_SEPARATOR + searchParamsId + MessageText.BUTTON_VALUE_SEPARATOR +
                page + MessageText.BUTTON_VALUE_SEPARATOR + id);

        InlineKeyboardButton appliedButton = new InlineKeyboardButton(MessageText.BUTTON_YES_CHECK_BOX +
                MessageText.SPACE + MessageText.BUTTON_APPLIED);
        appliedButton.setCallbackData(state + MessageText.BUTTON_VALUE_SEPARATOR +
                MessageText.APPLY + MessageText.BUTTON_VALUE_SEPARATOR + searchParamsId +
                MessageText.BUTTON_VALUE_SEPARATOR + page + MessageText.BUTTON_VALUE_SEPARATOR + id);


        InlineKeyboardRow row1 = new InlineKeyboardRow();
        InlineKeyboardRow row2 = new InlineKeyboardRow();

        InlineKeyboardButton firstButton;
        InlineKeyboardButton previousButton;
        if (page > 1) {
            firstButton = new InlineKeyboardButton(MessageText.BUTTON_FIRST_ARROW + MessageText.SPACE +
                    MessageText.BUTTON_FIRST);
            firstButton.setCallbackData(state + MessageText.BUTTON_VALUE_SEPARATOR + MessageText.FIRST +
                    MessageText.BUTTON_VALUE_SEPARATOR + searchParamsId);
            row2.add(firstButton);

            previousButton = new InlineKeyboardButton(MessageText.BUTTON_PREVIOUS_ARROW + MessageText.SPACE +
                    MessageText.BUTTON_PREVIOUS);
            previousButton.setCallbackData(state + MessageText.BUTTON_VALUE_SEPARATOR + MessageText.PREVIOUS +
                    MessageText.BUTTON_VALUE_SEPARATOR + searchParamsId + MessageText.BUTTON_VALUE_SEPARATOR +
                    page + MessageText.BUTTON_VALUE_SEPARATOR + id);
            row2.add(previousButton);
        } else {
            firstButton = new InlineKeyboardButton(MessageText.BUTTON_DISABLED);
            firstButton.setCallbackData(MessageText.NA);
            row2.add(firstButton);

            previousButton = new InlineKeyboardButton(MessageText.BUTTON_DISABLED);
            previousButton.setCallbackData(MessageText.NA);
            row2.add(previousButton);
        }



        switch (state){
            case MessageText.NEW -> {
                row1.add(deleteButton);
                row1.add(appliedButton);
            }
            case MessageText.SEARCHES -> {
                InlineKeyboardButton resultsButton = new InlineKeyboardButton(MessageText.BUTTON_RESULTS_EMOJI +
                        MessageText.SPACE + MessageText.BUTTON_RESULTS);
                resultsButton.setCallbackData(state + MessageText.BUTTON_VALUE_SEPARATOR + MessageText.RESULTS +
                        MessageText.BUTTON_VALUE_SEPARATOR + searchParamsId + MessageText.BUTTON_VALUE_SEPARATOR +
                        page + MessageText.BUTTON_VALUE_SEPARATOR + id);

                InlineKeyboardButton editButton = new InlineKeyboardButton(MessageText.BUTTON_EDIT_EMOJI +
                        MessageText.SPACE + MessageText.BUTTON_EDIT);
                editButton.setCallbackData(state + MessageText.BUTTON_VALUE_SEPARATOR + MessageText.EDIT +
                        MessageText.BUTTON_VALUE_SEPARATOR + searchParamsId + MessageText.BUTTON_VALUE_SEPARATOR +
                        page + MessageText.BUTTON_VALUE_SEPARATOR + id);

                row1.add(deleteButton);
                row1.add(editButton);
                row1.add(resultsButton);
            }
            case MessageText.APPLIED -> row1.add(rejectButton);
        }

        InlineKeyboardButton nextButton;
        InlineKeyboardButton lastButton;
        if (page < size) {
            nextButton = new InlineKeyboardButton(MessageText.BUTTON_NEXT_ARROW + MessageText.SPACE +
                    MessageText.BUTTON_NEXT);
            nextButton.setCallbackData(state + MessageText.BUTTON_VALUE_SEPARATOR + MessageText.NEXT +
                    MessageText.BUTTON_VALUE_SEPARATOR + searchParamsId + MessageText.BUTTON_VALUE_SEPARATOR +
                    page + MessageText.BUTTON_VALUE_SEPARATOR + id);
            row2.add(nextButton);
            lastButton = new InlineKeyboardButton(MessageText.BUTTON_LAST_ARROW + MessageText.SPACE +
                    MessageText.BUTTON_LAST);
            lastButton.setCallbackData(state + MessageText.BUTTON_VALUE_SEPARATOR + MessageText.LAST +
                    MessageText.BUTTON_VALUE_SEPARATOR + searchParamsId);
            row2.add(lastButton);
        } else {
            nextButton = new InlineKeyboardButton(MessageText.BUTTON_DISABLED);
            nextButton.setCallbackData(MessageText.NA);
            row2.add(nextButton);

            lastButton = new InlineKeyboardButton(MessageText.BUTTON_DISABLED);
            lastButton.setCallbackData(MessageText.NA);
            row2.add(lastButton);
        }
        List<InlineKeyboardRow> rows = new ArrayList<>();
        rows.add(row1);
        rows.add(row2);
        return new InlineKeyboardMarkup(rows);
    }
}

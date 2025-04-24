package com.gbyzzz.linkedinjobsbot.controller.command.keyboard;

import com.gbyzzz.linkedinjobsbot.modules.commons.values.MessageText;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class WorkplaceKeyboard {
    public static boolean[] state = new boolean[3];

    public InlineKeyboardMarkup getReplyButtons() {
        InlineKeyboardButton onSiteButton = new InlineKeyboardButton(state[0] ? MessageText.BUTTON_YES_CHECK_BOX + MessageText.SPACE +
                MessageText.BUTTON_ON_SITE : MessageText.BUTTON_NO_CHECK_BOX +
                MessageText.SPACE + MessageText.BUTTON_ON_SITE);
        onSiteButton.setCallbackData(MessageText.BUTTON_ON_SITE_VALUE);

        InlineKeyboardButton remoteButton = new InlineKeyboardButton(state[1] ? MessageText.BUTTON_YES_CHECK_BOX + MessageText.SPACE +
                MessageText.BUTTON_REMOTE : MessageText.BUTTON_NO_CHECK_BOX +
                MessageText.SPACE + MessageText.BUTTON_REMOTE);
        remoteButton.setCallbackData(MessageText.BUTTON_REMOTE_VALUE);

        InlineKeyboardButton hybridButton = new InlineKeyboardButton(state[2] ? MessageText.BUTTON_YES_CHECK_BOX + MessageText.SPACE +
                MessageText.BUTTON_HYBRID : MessageText.BUTTON_NO_CHECK_BOX +
                MessageText.SPACE + MessageText.BUTTON_HYBRID);
        hybridButton.setCallbackData(MessageText.BUTTON_HYBRID_VALUE);

        InlineKeyboardButton allWorkplaceButton = new InlineKeyboardButton(MessageText.BUTTON_ALL);
        allWorkplaceButton.setCallbackData(MessageText.BUTTON_ALL_WORKPLACE);

        InlineKeyboardButton nextButton = new InlineKeyboardButton(MessageText.BUTTON_NEXT);
        nextButton.setCallbackData(MessageText.NEXT);

        InlineKeyboardRow row1 = new InlineKeyboardRow();
        row1.add(onSiteButton);
        row1.add(remoteButton);

        InlineKeyboardRow row2 = new InlineKeyboardRow();
        row2.add(hybridButton);


        InlineKeyboardRow row3 = new InlineKeyboardRow();
        row3.add(allWorkplaceButton);

        InlineKeyboardRow row4 = new InlineKeyboardRow();
        row4.add(nextButton);

        List<InlineKeyboardRow> rows = new ArrayList<>();
        rows.add(row1);
        rows.add(row2);
        rows.add(row3);
        rows.add(row4);

        return new InlineKeyboardMarkup(rows);
    }

    public static void setWorkplaceKeyboardState(boolean value) {
        Arrays.fill(state, value);

    }

    public static String getWorkplaceValue() {
        StringBuilder value = new StringBuilder();
        for (int i = 0; i < state.length; i++) {
            if (state[i]) {
                value.append(i + 1);
                value.append(MessageText.COMMA);
            }
        }
        if (!value.isEmpty()) {
           return value.replace(value.length() - 1, value.length(), MessageText.EMPTY).toString();
        } else {
            return MessageText.EMPTY;
        }
    }

    public static void putWorkplaceValue(String value) {
        String[] values = value.split(MessageText.COMMA);
        for (String s : values) {
            state[Integer.parseInt(s) - 1] = true;
        }
    }

    public static void getWorkplaceCallbackAction(String data) {

        switch (data) {
            case MessageText.BUTTON_ON_SITE_VALUE -> state[0] = !state[0];
            case MessageText.BUTTON_REMOTE_VALUE -> state[1] = !state[1];
            case MessageText.BUTTON_HYBRID_VALUE -> state[2] = !state[2];
            case MessageText.BUTTON_ALL_WORKPLACE -> setWorkplaceKeyboardState(true);
        }
    }
}

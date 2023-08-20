package com.gbyzzz.linkedinjobsbot.controller.command.keyboard;

import com.gbyzzz.linkedinjobsbot.controller.MessageText;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class WorkplaceKeyboard {
    public static boolean[] state = new boolean[3];

    public InlineKeyboardMarkup getReplyButtons() {
        InlineKeyboardButton onSiteButton = new InlineKeyboardButton();
        onSiteButton.setText(state[0] ? MessageText.BUTTON_YES_CHECK_BOX + MessageText.SPACE +
                MessageText.BUTTON_ON_SITE : MessageText.BUTTON_NO_CHECK_BOX +
                MessageText.SPACE + MessageText.BUTTON_ON_SITE);
        onSiteButton.setCallbackData(MessageText.BUTTON_ON_SITE_VALUE);

        InlineKeyboardButton remoteButton = new InlineKeyboardButton();
        remoteButton.setText(state[1] ? MessageText.BUTTON_YES_CHECK_BOX + MessageText.SPACE +
                MessageText.BUTTON_REMOTE : MessageText.BUTTON_NO_CHECK_BOX +
                MessageText.SPACE + MessageText.BUTTON_REMOTE);
        remoteButton.setCallbackData(MessageText.BUTTON_REMOTE_VALUE);

        InlineKeyboardButton hybridButton = new InlineKeyboardButton();
        hybridButton.setText(state[2] ? MessageText.BUTTON_YES_CHECK_BOX + MessageText.SPACE +
                MessageText.BUTTON_HYBRID : MessageText.BUTTON_NO_CHECK_BOX +
                MessageText.SPACE + MessageText.BUTTON_HYBRID);
        hybridButton.setCallbackData(MessageText.BUTTON_HYBRID_VALUE);

        InlineKeyboardButton nextButton = new InlineKeyboardButton();
        nextButton.setText(MessageText.BUTTON_NEXT);
        nextButton.setCallbackData(MessageText.NEXT);

        List<InlineKeyboardButton> row1 = new ArrayList<>();
        row1.add(onSiteButton);
        row1.add(remoteButton);

        List<InlineKeyboardButton> row2 = new ArrayList<>();
        row2.add(hybridButton);

        List<InlineKeyboardButton> row3 = new ArrayList<>();
        row3.add(nextButton);

        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        rows.add(row1);
        rows.add(row2);
        rows.add(row3);

        return new InlineKeyboardMarkup(rows);
    }

    public static void setWorkplaceKeyboardFalse() {
        for (int i = 0; i<state.length; i++) {
            state[i] = false;
        }
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
            value.replace(value.length() - 1, value.length(), MessageText.EMPTY);
        }
        return value.toString();
    }

    public static void getWorkplaceCallbackAction(String data) {

        switch (data) {
            case MessageText.BUTTON_ON_SITE_VALUE -> state[0] = !state[0];
            case MessageText.BUTTON_REMOTE_VALUE -> state[1] = !state[1];
            case MessageText.BUTTON_HYBRID_VALUE -> state[2] = !state[2];
        }
    }
}

package com.gbyzzz.linkedinjobsbot.controller.command.keyboard;

import com.gbyzzz.linkedinjobsbot.controller.MessageText;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class ExperienceKeyboard {
    public static boolean[] state = new boolean[6];

    public InlineKeyboardMarkup getReplyButtons() {
        InlineKeyboardButton internshipButton = new InlineKeyboardButton(state[0] ? MessageText.BUTTON_YES_CHECK_BOX + MessageText.SPACE +
                MessageText.BUTTON_INTERNSHIP : MessageText.BUTTON_NO_CHECK_BOX +
                MessageText.SPACE + MessageText.BUTTON_INTERNSHIP);
        internshipButton.setCallbackData(MessageText.BUTTON_INTERNSHIP_VALUE);

        InlineKeyboardButton entryButton = new InlineKeyboardButton(state[1] ? MessageText.BUTTON_YES_CHECK_BOX + MessageText.SPACE +
                MessageText.BUTTON_ENTRY_LEVEL : MessageText.BUTTON_NO_CHECK_BOX +
                MessageText.SPACE + MessageText.BUTTON_ENTRY_LEVEL);
        entryButton.setCallbackData(MessageText.BUTTON_ENTRY_LEVEL_VALUE);

        InlineKeyboardButton associateButton = new InlineKeyboardButton(state[2] ? MessageText.BUTTON_YES_CHECK_BOX + MessageText.SPACE +
                MessageText.BUTTON_ASSOCIATE : MessageText.BUTTON_NO_CHECK_BOX +
                MessageText.SPACE + MessageText.BUTTON_ASSOCIATE);
        associateButton.setCallbackData(MessageText.BUTTON_ASSOCIATE_VALUE);

        InlineKeyboardButton midSeniorButton = new InlineKeyboardButton(state[3] ? MessageText.BUTTON_YES_CHECK_BOX + MessageText.SPACE +
                MessageText.BUTTON_MID_SENIOR_LEVEL : MessageText.BUTTON_NO_CHECK_BOX +
                MessageText.SPACE + MessageText.BUTTON_MID_SENIOR_LEVEL);
        midSeniorButton.setCallbackData(MessageText.BUTTON_MID_SENIOR_LEVEL_VALUE);

        InlineKeyboardButton directorButton = new InlineKeyboardButton(state[4] ? MessageText.BUTTON_YES_CHECK_BOX + MessageText.SPACE +
                MessageText.BUTTON_DIRECTOR : MessageText.BUTTON_NO_CHECK_BOX +
                MessageText.SPACE + MessageText.BUTTON_DIRECTOR);
        directorButton.setCallbackData(MessageText.BUTTON_DIRECTOR_VALUE);

        InlineKeyboardButton executiveButton = new InlineKeyboardButton(state[5] ? MessageText.BUTTON_YES_CHECK_BOX + MessageText.SPACE +
                MessageText.BUTTON_EXECUTIVE : MessageText.BUTTON_NO_CHECK_BOX +
                MessageText.SPACE + MessageText.BUTTON_EXECUTIVE);
        executiveButton.setCallbackData(MessageText.BUTTON_EXECUTIVE_VALUE);

        InlineKeyboardButton allExperienceButton = new InlineKeyboardButton(MessageText.BUTTON_ALL);
        allExperienceButton.setCallbackData(MessageText.BUTTON_ALL_EXPERIENCE);

        InlineKeyboardButton nextButton = new InlineKeyboardButton(MessageText.BUTTON_NEXT);
        nextButton.setCallbackData(MessageText.NEXT);

        InlineKeyboardRow row1 = new InlineKeyboardRow();
        row1.add(internshipButton);
        row1.add(entryButton);

        InlineKeyboardRow row2 = new InlineKeyboardRow();
        row2.add(associateButton);
        row2.add(midSeniorButton);

        InlineKeyboardRow row3 = new InlineKeyboardRow();
        row3.add(directorButton);
        row3.add(executiveButton);
        InlineKeyboardRow row4 = new InlineKeyboardRow();
        row4.add(allExperienceButton);

        InlineKeyboardRow row5 = new InlineKeyboardRow();
        row5.add(nextButton);

        List<InlineKeyboardRow> rows = new ArrayList<>();
        rows.add(row1);
        rows.add(row2);
        rows.add(row3);
        rows.add(row4);
        rows.add(row5);

        return new InlineKeyboardMarkup(rows);
    }

    public static void setExperienceKeyboardState(boolean value) {
        Arrays.fill(state, value);
    }

    public static String getExperienceValue() {
        StringBuilder value = new StringBuilder();
        for (int i = 0; i < state.length; i++) {
            if (state[i]) {
                value.append(i + 1).append(MessageText.COMMA);
            }
        }
        if (!value.isEmpty()) {
            value.replace(value.length() - 1, value.length(), MessageText.EMPTY);
        }
        return value.toString();
    }

    public static void putExperienceValue(String value) {
        String[] values = value.split(MessageText.COMMA);
        for (String s : values) {
            state[Integer.parseInt(s) - 1] = true;
        }
    }

    public static void getExperienceCallbackAction(String data) {

        switch (data) {
            case MessageText.BUTTON_INTERNSHIP_VALUE -> state[0] = !state[0];
            case MessageText.BUTTON_ENTRY_LEVEL_VALUE -> state[1] = !state[1];
            case MessageText.BUTTON_ASSOCIATE_VALUE -> state[2] = !state[2];
            case MessageText.BUTTON_MID_SENIOR_LEVEL_VALUE -> state[3] = !state[3];
            case MessageText.BUTTON_DIRECTOR_VALUE -> state[4] = !state[4];
            case MessageText.BUTTON_EXECUTIVE_VALUE -> state[5] = !state[5];
            case MessageText.BUTTON_ALL_EXPERIENCE -> setExperienceKeyboardState(true);

        }
    }
}

package com.gbyzzz.linkedinjobsbot.controller.command.keyboard;

import com.gbyzzz.linkedinjobsbot.controller.MessageText;
import com.gbyzzz.linkedinjobsbot.service.impl.Experience;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class ExperienceKeyboard {
    public static boolean[] state = new boolean[6];

    public InlineKeyboardMarkup getReplyButtons() {
        InlineKeyboardButton internshipButton = new InlineKeyboardButton();
        internshipButton.setText(state[0] ? MessageText.BUTTON_YES_CHECK_BOX + MessageText.SPACE +
                MessageText.BUTTON_INTERNSHIP : MessageText.BUTTON_NO_CHECK_BOX +
                MessageText.SPACE + MessageText.BUTTON_INTERNSHIP);
        internshipButton.setCallbackData(MessageText.BUTTON_INTERNSHIP_VALUE);

        InlineKeyboardButton entryButton = new InlineKeyboardButton();
        entryButton.setText(state[1] ? MessageText.BUTTON_YES_CHECK_BOX + MessageText.SPACE +
                MessageText.BUTTON_ENTRY_LEVEL : MessageText.BUTTON_NO_CHECK_BOX +
                MessageText.SPACE + MessageText.BUTTON_ENTRY_LEVEL);
        entryButton.setCallbackData(MessageText.BUTTON_ENTRY_LEVEL_VALUE);

        InlineKeyboardButton associateButton = new InlineKeyboardButton();
        associateButton.setText(state[2] ? MessageText.BUTTON_YES_CHECK_BOX + MessageText.SPACE +
                MessageText.BUTTON_ASSOCIATE : MessageText.BUTTON_NO_CHECK_BOX +
                MessageText.SPACE + MessageText.BUTTON_ASSOCIATE);
        associateButton.setCallbackData(MessageText.BUTTON_ASSOCIATE_VALUE);

        InlineKeyboardButton midSeniorButton = new InlineKeyboardButton();
        midSeniorButton.setText(state[3] ? MessageText.BUTTON_YES_CHECK_BOX + MessageText.SPACE +
                MessageText.BUTTON_MID_SENIOR_LEVEL : MessageText.BUTTON_NO_CHECK_BOX +
                MessageText.SPACE + MessageText.BUTTON_MID_SENIOR_LEVEL);
        midSeniorButton.setCallbackData(MessageText.BUTTON_MID_SENIOR_LEVEL_VALUE);

        InlineKeyboardButton directorButton = new InlineKeyboardButton();
        directorButton.setText(state[4] ? MessageText.BUTTON_YES_CHECK_BOX + MessageText.SPACE +
                MessageText.BUTTON_DIRECTOR : MessageText.BUTTON_NO_CHECK_BOX +
                MessageText.SPACE + MessageText.BUTTON_DIRECTOR);
        directorButton.setCallbackData(MessageText.BUTTON_DIRECTOR_VALUE);

        InlineKeyboardButton executiveButton = new InlineKeyboardButton();
        executiveButton.setText(state[5] ? MessageText.BUTTON_YES_CHECK_BOX + MessageText.SPACE +
                MessageText.BUTTON_EXECUTIVE : MessageText.BUTTON_NO_CHECK_BOX +
                MessageText.SPACE + MessageText.BUTTON_EXECUTIVE);
        executiveButton.setCallbackData(MessageText.BUTTON_EXECUTIVE_VALUE);

        InlineKeyboardButton nextButton = new InlineKeyboardButton();
        nextButton.setText(MessageText.BUTTON_NEXT);
        nextButton.setCallbackData(MessageText.NEXT);

        List<InlineKeyboardButton> row1 = new ArrayList<>();
        row1.add(internshipButton);
        row1.add(entryButton);

        List<InlineKeyboardButton> row2 = new ArrayList<>();
        row2.add(associateButton);
        row2.add(midSeniorButton);

        List<InlineKeyboardButton> row3 = new ArrayList<>();
        row3.add(directorButton);
        row3.add(executiveButton);
        List<InlineKeyboardButton> row4 = new ArrayList<>();
        row4.add(nextButton);

        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        rows.add(row1);
        rows.add(row2);
        rows.add(row3);
        rows.add(row4);

        return new InlineKeyboardMarkup(rows);
    }

    public static void setExperienceKeyboardFalse() {
        Arrays.fill(state, false);
    }

    public static String getExperienceValue() {
        StringBuilder value = new StringBuilder();
        for (int i = 0; i < state.length; i++) {
            if (state[i]){
                value.append(i+1).append(MessageText.COMMA);
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
        }
    }
}

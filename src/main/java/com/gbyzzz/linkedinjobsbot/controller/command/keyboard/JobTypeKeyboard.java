package com.gbyzzz.linkedinjobsbot.controller.command.keyboard;

import com.gbyzzz.linkedinjobsbot.controller.MessageText;
import com.gbyzzz.linkedinjobsbot.service.impl.JobTypes;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class JobTypeKeyboard {
    public static boolean[] state = new boolean[5];

    public InlineKeyboardMarkup getReplyButtons() {
        InlineKeyboardButton fullTimeButton = new InlineKeyboardButton();
        fullTimeButton.setText(state[0] ? MessageText.BUTTON_YES_CHECK_BOX + MessageText.SPACE +
                MessageText.BUTTON_FULL_TIME : MessageText.BUTTON_NO_CHECK_BOX +
                MessageText.SPACE + MessageText.BUTTON_FULL_TIME);
        fullTimeButton.setCallbackData(MessageText.BUTTON_FULL_TIME_VALUE);

        InlineKeyboardButton partTimeButton = new InlineKeyboardButton();
        partTimeButton.setText(state[1] ? MessageText.BUTTON_YES_CHECK_BOX + MessageText.SPACE +
                MessageText.BUTTON_PART_TIME : MessageText.BUTTON_NO_CHECK_BOX +
                MessageText.SPACE + MessageText.BUTTON_PART_TIME);
        partTimeButton.setCallbackData(MessageText.BUTTON_PART_TIME_VALUE);

        InlineKeyboardButton contractButton = new InlineKeyboardButton();
        contractButton.setText(state[2] ? MessageText.BUTTON_YES_CHECK_BOX + MessageText.SPACE +
                MessageText.BUTTON_CONTRACT : MessageText.BUTTON_NO_CHECK_BOX +
                MessageText.SPACE + MessageText.BUTTON_CONTRACT);
        contractButton.setCallbackData(MessageText.BUTTON_CONTRACT_VALUE);

        InlineKeyboardButton temporaryButton = new InlineKeyboardButton();
        temporaryButton.setText(state[3] ? MessageText.BUTTON_YES_CHECK_BOX + MessageText.SPACE +
                MessageText.BUTTON_TEMPORARY : MessageText.BUTTON_NO_CHECK_BOX +
                MessageText.SPACE + MessageText.BUTTON_TEMPORARY);
        temporaryButton.setCallbackData(MessageText.BUTTON_TEMPORARY_VALUE);

        InlineKeyboardButton internshipButton = new InlineKeyboardButton();
        internshipButton.setText(state[4] ? MessageText.BUTTON_YES_CHECK_BOX + MessageText.SPACE +
                MessageText.BUTTON_INTERNSHIP : MessageText.BUTTON_NO_CHECK_BOX +
                MessageText.SPACE + MessageText.BUTTON_INTERNSHIP);
        internshipButton.setCallbackData(MessageText.BUTTON_INTERNSHIP_VALUE);

        InlineKeyboardButton allJobTypeButton = new InlineKeyboardButton();
        allJobTypeButton.setText(MessageText.BUTTON_ALL);
        allJobTypeButton.setCallbackData(MessageText.BUTTON_ALL_JOB_TYPE);

        InlineKeyboardButton nextButton = new InlineKeyboardButton();
        nextButton.setText(MessageText.BUTTON_NEXT);
        nextButton.setCallbackData(MessageText.NEXT);

        List<InlineKeyboardButton> row1 = new ArrayList<>();
        row1.add(fullTimeButton);
        row1.add(partTimeButton);

        List<InlineKeyboardButton> row2 = new ArrayList<>();
        row2.add(contractButton);
        row2.add(temporaryButton);

        List<InlineKeyboardButton> row3 = new ArrayList<>();
        row3.add(internshipButton);

        List<InlineKeyboardButton> row4 = new ArrayList<>();
        row4.add(allJobTypeButton);

        List<InlineKeyboardButton> row5 = new ArrayList<>();
        row5.add(nextButton);

        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        rows.add(row1);
        rows.add(row2);
        rows.add(row3);
        rows.add(row4);
        rows.add(row5);

        return new InlineKeyboardMarkup(rows);
    }

    public static void setJobTypeKeyboardState(boolean value) {
        Arrays.fill(state, value);
    }

    public static String getJobTypeValue() {
        StringBuilder value = new StringBuilder();
        for (int i = 0; i < state.length; i++) {
            if (state[i]) {
                value.append(JobTypes.getValue(i));
                value.append(MessageText.COMMA);
            }
        }
        if (!value.isEmpty()) {
            value.replace(value.length() - 1, value.length(), MessageText.EMPTY);
        }
        return value.toString();
    }

    public static void PutJobTypeValue(String value) {
        String[] values = value.split(MessageText.COMMA);
        for (String s : values) {
            state[JobTypes.getStateId(s)] = true;
        }
    }


    public static void getJobTypeCallbackAction(String data) {

        switch (data) {
            case MessageText.BUTTON_FULL_TIME_VALUE -> state[0] = !state[0];
            case MessageText.BUTTON_PART_TIME_VALUE -> state[1] = !state[1];
            case MessageText.BUTTON_CONTRACT_VALUE -> state[2] = !state[2];
            case MessageText.BUTTON_TEMPORARY_VALUE -> state[3] = !state[3];
            case MessageText.BUTTON_INTERNSHIP_VALUE -> state[4] = !state[4];
            case MessageText.BUTTON_ALL_JOB_TYPE -> setJobTypeKeyboardState(true);
        }
    }

}

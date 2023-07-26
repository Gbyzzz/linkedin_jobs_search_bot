package com.gbyzzz.linkedinjobsbot.controller.command.keyboard;

import com.gbyzzz.linkedinjobsbot.service.impl.JobTypes;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class JobTypeKeyboard {
    public static boolean[] state = new boolean[5];

    public InlineKeyboardMarkup getReplyButtons() {
        InlineKeyboardButton fullTimeButton = new InlineKeyboardButton();
        fullTimeButton.setText(state[0] ? "✅ Full-time" : "❌ Full-time");
        fullTimeButton.setCallbackData("toggle_full_time");

        InlineKeyboardButton partTimeButton = new InlineKeyboardButton();
        partTimeButton.setText(state[1] ? "✅ Part-time" : "❌ Part-time");
        partTimeButton.setCallbackData("toggle_part_time");

        InlineKeyboardButton contractButton = new InlineKeyboardButton();
        contractButton.setText(state[2] ? "✅ Contract" : "❌ Contract");
        contractButton.setCallbackData("toggle_contract");

        InlineKeyboardButton temporaryButton = new InlineKeyboardButton();
        temporaryButton.setText(state[3] ? "✅ Temporary" : "❌ Temporary");
        temporaryButton.setCallbackData("toggle_temporary");

        InlineKeyboardButton internshipButton = new InlineKeyboardButton();
        internshipButton.setText(state[4] ? "✅ Internship" : "❌ Internship");
        internshipButton.setCallbackData("toggle_internship");

        InlineKeyboardButton nextButton = new InlineKeyboardButton();
        nextButton.setText("Next");
        nextButton.setCallbackData("next");

        List<InlineKeyboardButton> row1 = new ArrayList<>();
        row1.add(fullTimeButton);
        row1.add(partTimeButton);

        List<InlineKeyboardButton> row2 = new ArrayList<>();
        row2.add(contractButton);
        row2.add(temporaryButton);

        List<InlineKeyboardButton> row3 = new ArrayList<>();
        row3.add(internshipButton);
        List<InlineKeyboardButton> row4 = new ArrayList<>();
        row4.add(nextButton);

        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        rows.add(row1);
        rows.add(row2);
        rows.add(row3);
        rows.add(row4);

        return new InlineKeyboardMarkup(rows);
    }

    public static void setJobTypeKeyboardFalse() {
        for (boolean s : state) {
            s = false;
        }
    }

    public static String getJobTypeValue() {
        StringBuilder value = new StringBuilder();
        for (int i = 0; i < state.length; i++) {
            if (state[i]) {
                value.append(JobTypes.getValue(i));
                value.append(",");
            }
        }
        if (value.length() > 0) {
            value.replace(value.length() - 1, value.length(), "");
        }
        return value.toString();
    }


    public static void getJobTypeCallbackAction(String data) {

        switch (data) {
            case "toggle_full_time" -> state[0] = !state[0];
            case "toggle_part_time" -> state[1] = !state[1];
            case "toggle_contract" -> state[2] = !state[2];
            case "toggle_temporary" -> state[3] = !state[3];
            case "toggle_internship" -> state[4] = !state[4];
        }
    }

}

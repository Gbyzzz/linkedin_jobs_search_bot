package com.gbyzzz.linkedinjobsbot.controller.command.keyboard;

import com.gbyzzz.linkedinjobsbot.service.impl.Experience;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class ExperienceKeyboard {
    public static boolean[] state = new boolean[6];

    public InlineKeyboardMarkup getReplyButtons() {
        InlineKeyboardButton internshipButton = new InlineKeyboardButton();
        internshipButton.setText(state[0] ? "✅ Internship" : "❌ Internship");
        internshipButton.setCallbackData("toggle_internship");

        InlineKeyboardButton entryButton = new InlineKeyboardButton();
        entryButton.setText(state[1] ? "✅ Entry Level" : "❌ Entry Level");
        entryButton.setCallbackData("toggle_entry");

        InlineKeyboardButton associateButton = new InlineKeyboardButton();
        associateButton.setText(state[2] ? "✅ Associate" : "❌ Associate");
        associateButton.setCallbackData("toggle_associate");

        InlineKeyboardButton midSeniorButton = new InlineKeyboardButton();
        midSeniorButton.setText(state[3] ? "✅ Mid-Senior Level" : "❌ Mid-Senior Level");
        midSeniorButton.setCallbackData("toggle_mid_senior");

        InlineKeyboardButton directorButton = new InlineKeyboardButton();
        directorButton.setText(state[4] ? "✅ Director" : "❌ Director");
        directorButton.setCallbackData("toggle_director");

        InlineKeyboardButton executiveButton = new InlineKeyboardButton();
        executiveButton.setText(state[5] ? "✅ Executive" : "❌ Executive");
        executiveButton.setCallbackData("toggle_executive");

        InlineKeyboardButton nextButton = new InlineKeyboardButton();
        nextButton.setText("Next");
        nextButton.setCallbackData("next");

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
        for (int i = 0; i<state.length; i++) {
            state[i] = false;
        }
    }

    public static String getExperienceValue() {
        StringBuilder value = new StringBuilder();
        for (int i = 0; i < state.length; i++) {
            if (state[i]){
                value.append(i+1);
                value.append(",");
            }
        }
        if (value.length() > 0) {
            value.replace(value.length() - 1, value.length(), "");
        }
        return value.toString();
    }

    public static void getExperienceCallbackAction(String data) {

        switch (data) {
            case "toggle_internship" -> state[0] = !state[0];
            case "toggle_entry" -> state[1] = !state[1];
            case "toggle_associate" -> state[2] = !state[2];
            case "toggle_mid_senior" -> state[3] = !state[3];
            case "toggle_director" -> state[4] = !state[4];
            case "toggle_executive" -> state[5] = !state[5];
        }
    }
}

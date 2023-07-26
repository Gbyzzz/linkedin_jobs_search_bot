package com.gbyzzz.linkedinjobsbot.controller.command.keyboard;

import com.gbyzzz.linkedinjobsbot.service.impl.JobTypes;
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
        onSiteButton.setText(state[0] ? "✅ On-site" : "❌ On-site");
        onSiteButton.setCallbackData("toggle_on_site");

        InlineKeyboardButton remoteButton = new InlineKeyboardButton();
        remoteButton.setText(state[1] ? "✅ Remote" : "❌ Remote");
        remoteButton.setCallbackData("toggle_remote");

        InlineKeyboardButton hybridButton = new InlineKeyboardButton();
        hybridButton.setText(state[2] ? "✅ Hybrid" : "❌ Hybrid");
        hybridButton.setCallbackData("toggle_hybrid");

        InlineKeyboardButton nextButton = new InlineKeyboardButton();
        nextButton.setText("Next");
        nextButton.setCallbackData("next");

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
        for (boolean s : state) {
            s = false;
        }
    }

    public static String getWorkplaceValue() {
        StringBuilder value = new StringBuilder();
        for (int i = 0; i < state.length; i++) {
            if (state[i]) {
                value.append(i + 1);
                value.append(",");
            }
        }
        if (value.length() > 0) {
            value.replace(value.length() - 1, value.length(), "");
        }
        return value.toString();
    }

    public static void getWorkplaceCallbackAction(String data) {

        switch (data) {
            case "toggle_on_site" -> state[0] = !state[0];
            case "toggle_remote" -> state[1] = !state[1];
            case "toggle_hybrid" -> state[2] = !state[2];
        }
    }
}

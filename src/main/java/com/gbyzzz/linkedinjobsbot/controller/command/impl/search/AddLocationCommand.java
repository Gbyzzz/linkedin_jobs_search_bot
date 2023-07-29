package com.gbyzzz.linkedinjobsbot.controller.command.impl.search;

import com.gbyzzz.linkedinjobsbot.controller.command.Command;
import com.gbyzzz.linkedinjobsbot.controller.command.keyboard.ExperienceKeyboard;
import com.gbyzzz.linkedinjobsbot.entity.SearchParams;
import com.gbyzzz.linkedinjobsbot.entity.UserProfile;
import com.gbyzzz.linkedinjobsbot.service.RedisService;
import com.gbyzzz.linkedinjobsbot.service.SearchParamsService;
import com.gbyzzz.linkedinjobsbot.service.UserProfileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import static com.gbyzzz.linkedinjobsbot.controller.command.keyboard.ExperienceKeyboard.*;

@Component("ADD_LOCATION")
@AllArgsConstructor
public class AddLocationCommand extends BotCommand implements Command {

    private final UserProfileService userProfileService;
    private final ExperienceKeyboard experienceKeyboard;
    private final RedisService redisService;
    private static final String REPLY_TEXT = "Search parameters added\n" +
            "Now you can add additional search parameters, experience :";

    @Override
    public SendMessage execute(Update update) {
        Long id = update.getMessage().getChatId();
        SendMessage sendMessage;
        sendMessage = new SendMessage(id.toString(), REPLY_TEXT);
        SearchParams searchParams = (SearchParams) redisService.getFromTempRepository(id);
        searchParams.setLocation(update.getMessage().getText());
        redisService.saveToTempRepository(searchParams, id);
        UserProfile userProfile = userProfileService
                .getUserProfileById(id).orElse(null);
        userProfile.setBotState(UserProfile.BotState.ADD_EXPERIENCE);
        userProfileService.save(userProfile);
        setExperienceKeyboardFalse();
        sendMessage.setReplyMarkup(experienceKeyboard.getReplyButtons());
        return sendMessage;

    }


//    private InlineKeyboardMarkup getReplyButtons(SendMessage sendMessage) {
//        InlineKeyboardButton internshipButton = new InlineKeyboardButton();
//        internshipButton.setText(internshipState ? "✅ Internship" : "❌ Internship");
//        internshipButton.setCallbackData("toggle_internship");
//
//        InlineKeyboardButton entryButton = new InlineKeyboardButton();
//        entryButton.setText(entryState ? "✅ Entry Level" : "❌ Entry Level");
//        entryButton.setCallbackData("toggle_entry");
//
//        InlineKeyboardButton associateButton = new InlineKeyboardButton();
//        associateButton.setText(associateState ? "✅ Associate" : "❌ Associate");
//        associateButton.setCallbackData("toggle_associate");
//
//        InlineKeyboardButton midSeniorButton = new InlineKeyboardButton();
//        midSeniorButton.setText(midSeniorState ? "✅ Mid-Senior Level" : "❌ Mid-Senior Level");
//        midSeniorButton.setCallbackData("toggle_mid_senior");
//
//        InlineKeyboardButton directorButton = new InlineKeyboardButton();
//        directorButton.setText(directorState ? "✅ Director" : "❌ Director");
//        directorButton.setCallbackData("toggle_director");
//
//        InlineKeyboardButton executiveButton = new InlineKeyboardButton();
//        executiveButton.setText(executiveState ? "✅ Executive" : "❌ Executive");
//        executiveButton.setCallbackData("toggle_executive");
//
//        InlineKeyboardButton nextButton = new InlineKeyboardButton();
//        executiveButton.setText("Next");
//        executiveButton.setCallbackData("toggle_next");
//
//        List<InlineKeyboardButton> row1 = new ArrayList<>();
//        row1.add(internshipButton);
//        row1.add(entryButton);
//
//        List<InlineKeyboardButton> row2 = new ArrayList<>();
//        row2.add(associateButton);
//        row2.add(midSeniorButton);
//
//        List<InlineKeyboardButton> row3 = new ArrayList<>();
//        row3.add(directorButton);
//        row3.add(executiveButton);
//        List<InlineKeyboardButton> row4 = new ArrayList<>();
//        row3.add(directorButton);
//        row3.add(executiveButton);
//
//        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
//        rows.add(row1);
//        rows.add(row2);
//        rows.add(row3);
//        rows.add(row4);
//
//        return new InlineKeyboardMarkup(rows);
//    }
}

package com.gbyzzz.linkedinjobsbot.controller.command.impl;

import com.gbyzzz.linkedinjobsbot.controller.command.Command;
import com.gbyzzz.linkedinjobsbot.controller.command.keyboard.PaginationKeyboard;
import com.gbyzzz.linkedinjobsbot.dto.Reply;
import com.gbyzzz.linkedinjobsbot.entity.SearchParams;
import com.gbyzzz.linkedinjobsbot.entity.UserProfile;
import com.gbyzzz.linkedinjobsbot.service.SearchParamsService;
import com.gbyzzz.linkedinjobsbot.service.UserProfileService;
import com.gbyzzz.linkedinjobsbot.service.impl.Experience;
import com.gbyzzz.linkedinjobsbot.service.impl.JobTypes;
import com.gbyzzz.linkedinjobsbot.service.impl.Workplace;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component("GET_ALL_SEARCHES")
@AllArgsConstructor
public class GetAllSearchesCommand implements Command {

    private final SearchParamsService searchParamsService;
    private final UserProfileService userProfileService;
    private final PaginationKeyboard paginationKeyboard;

    private static final String REPLY = "No search params found, please add one with /add_search";

    @Override
    public Reply execute(Update update) throws IOException {
        Long id = update.getMessage().getChatId();
        SendMessage sendMessage;
        List<SearchParams> searchParams = searchParamsService.findAllByUserId(id);

        if (!searchParams.isEmpty()) {
            SearchParams params = searchParams.get(0);
            UserProfile userProfile = userProfileService.getUserProfileById(id).get();
            userProfile.setBotState(UserProfile.BotState.SEARCHES);
            userProfileService.save(userProfile);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("1 of ").append(searchParams.size())
                    .append("\n").append("\n").append("Keywords: ");
            for (String keyword : params.getKeywords()) {
                stringBuilder.append(keyword).append(" ");
            }
            stringBuilder.append("\n");
            stringBuilder.append("Location: ").append(params.getLocation()).append("\n\n");
            stringBuilder.append("Search filters: \n");
            for (Map.Entry<String, String> entry : params.getSearchFilters().entrySet()) {
                stringBuilder.append("  ");
                stringBuilder.append(entry.getKey()).append(":\n");

                switch (entry.getKey()) {
                    case "workplaceType" -> {
                        for (String val : entry.getValue().split(",")) {
                            stringBuilder.append("    ");
                            stringBuilder.append(Workplace.getName(Integer.parseInt(val)));
                            stringBuilder.append("\n");
                        }
                    }
                    case "experience" -> {
                        for (String val : entry.getValue().split(",")) {
                            stringBuilder.append("    ");
                            stringBuilder.append(Experience.getName(val));
                            stringBuilder.append("\n");
                        }
                    }
                    case "jobType" -> {
                        for (String val : entry.getValue().split(",")) {
                            stringBuilder.append("    ");
                            stringBuilder.append(JobTypes.getName(val));
                            stringBuilder.append("\n");
                        }
                    }
                }
                stringBuilder.append("\n");
            }
            stringBuilder.append("Additional filters: \n");

            stringBuilder.append("  Include in description:\n");

            for (String include : params.getFilterParams().getIncludeWordsInDescription()) {
                stringBuilder.append("    ").append(include).append("\n");
            }
            stringBuilder.append("\n");
            stringBuilder.append("  Exclude in description:\n");

            for (String exclude : params.getFilterParams().getExcludeWordsFromTitle()) {
                stringBuilder.append("    ").append(exclude).append("\n");
            }
            stringBuilder.append("\n");
            sendMessage = new SendMessage(id.toString(),
                    stringBuilder.toString());
            sendMessage.setReplyMarkup(paginationKeyboard.getReplyButtons(0, searchParams.size(),
                    UserProfile.BotState.SEARCHES.name(), searchParams.get(0).getId().toString()));
        } else {
            sendMessage = new SendMessage(id.toString(), REPLY);
        }
        return new Reply(sendMessage, false);
    }
}

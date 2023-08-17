package com.gbyzzz.linkedinjobsbot.controller.command.impl;

import com.gbyzzz.linkedinjobsbot.controller.command.Command;
import com.gbyzzz.linkedinjobsbot.controller.command.keyboard.PaginationKeyboard;
import com.gbyzzz.linkedinjobsbot.dto.Reply;
import com.gbyzzz.linkedinjobsbot.entity.SavedJob;
import com.gbyzzz.linkedinjobsbot.entity.SearchParams;
import com.gbyzzz.linkedinjobsbot.entity.UserProfile;
import com.gbyzzz.linkedinjobsbot.service.SavedJobService;
import com.gbyzzz.linkedinjobsbot.service.SearchParamsService;
import com.gbyzzz.linkedinjobsbot.service.UserProfileService;
import com.gbyzzz.linkedinjobsbot.service.impl.Experience;
import com.gbyzzz.linkedinjobsbot.service.impl.JobTypes;
import com.gbyzzz.linkedinjobsbot.service.impl.Workplace;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component("WATCH_LIST_OF_JOBS")
@AllArgsConstructor
public class ListOfJobsCommand implements Command {

    private static List<SavedJob> jobs = new ArrayList<>();
    private static List<SearchParams> searchParams = new ArrayList<>();

    private final SavedJobService savedJobService;
    private final UserProfileService userProfileService;
    private final PaginationKeyboard paginationKeyboard;
    private final SearchParamsService searchParamsService;

    @Override
    public Reply execute(Update update) throws IOException {
        Long id = update.getCallbackQuery().getMessage().getChatId();
        String[] command = update.getCallbackQuery().getData().split("_");
        SendMessage sendMessage = null;
        Long targetId = null;

        switch (command[1]) {
            case "NEW" -> {
                updateJobs(id, command[2]);

                targetId = jobs.get(Integer.parseInt(command[3])).getJobId();
            }
            case "APPLIED" -> {
                jobs = savedJobService.getAppliedJobsByUserId(id);
                targetId = jobs.get(Integer.parseInt(command[3])).getJobId();
            }
            case "SEARCHES" -> searchParams = searchParamsService.findAllByUserId(id);
            case "SCHEDULED" -> jobs = savedJobService.getNewJobsByUserId(id);


        }

        switch (command[0]) {
            case "next", "previous" -> {
                int index = Integer.parseInt(command[3]);
                if (command[1].equals("NEW") ||
                        command[1].equals("APPLIED")) {
                    sendMessage = makeReply(index, command[1], id, command[2]);
                    sendMessage.setReplyMarkup(paginationKeyboard.getReplyButtons(
                            index, jobs.size(), command[1], command[2]));
                } else {
                    sendMessage = makeReply(index, command[1], id, command[2]);
                    sendMessage.setReplyMarkup(paginationKeyboard.getReplyButtons(
                            index, searchParams.size(), command[1], command[2]));
                }
            }
            case "apply" -> {
                SavedJob savedJob = savedJobService.getJobById(targetId).get();
                savedJobService.saveJob(savedJob);
                savedJob.setReplyState(SavedJob.ReplyState.APPLIED);
                savedJob.setDateApplied(new Date(System.currentTimeMillis()));
                savedJobService.saveJob(savedJob);
                jobs = savedJobService.getNewJobsByUserId(id);
                int index = (jobs.size() - 1) > Integer.parseInt(command[3]) ?
                        Integer.parseInt(command[3]) :
                        Integer.parseInt(command[3]) - 1;
                sendMessage = makeReply(index, command[1], id, command[2]);
            }
            case "delete" -> {
                switch (command[1]) {
                    case "NEW", "APPLY" -> {
                        SavedJob savedJob = savedJobService.getJobById(targetId).get();
                        savedJob.setReplyState(SavedJob.ReplyState.DELETED);
                        savedJobService.saveJob(savedJob);
                        jobs = savedJobService.getNewJobsByUserId(id);
                        int index = (jobs.size() - 1) > Integer.parseInt(command[3]) ?
                                Integer.parseInt(command[3]) :
                                Integer.parseInt(command[3]) - 1;
                        sendMessage = makeReply(index, command[1], id, command[2]);
                    }
                    case "SEARCHES" -> {
                        int index = (searchParams.size() - 1) > Integer.parseInt(command[3]) ?
                                Integer.parseInt(command[3]) :
                                Integer.parseInt(command[3]) - 1;
                        searchParamsService.delete(searchParams.get(Integer.parseInt(command[3])));
                        searchParams = searchParamsService.findAllByUserId(id);
                        sendMessage = makeReply(index, command[1], id, command[2]);
                    }
                }

            }
            case "rejected" -> {
                SavedJob savedJob = savedJobService.getJobById(targetId).get();
                savedJobService.saveJob(savedJob);
                savedJob.setReplyState(SavedJob.ReplyState.REJECTED);
                savedJobService.saveJob(savedJob);
                jobs = savedJobService.getAppliedJobsByUserId(id);
                int index = (jobs.size() - 1) > Integer.parseInt(command[3]) ?
                        Integer.parseInt(command[3]) :
                        Integer.parseInt(command[3]) - 1;
                sendMessage = makeReply(index, command[1], id, command[2]);
            }
            case "notify" -> sendMessage = makeReply(0, UserProfile.BotState.NEW.name(),
                    id, "ALL");
            case "results" -> {

                jobs = savedJobService.getNewJobsByUserIdAndSearchParams(id, searchParams
                        .get(Integer.parseInt(command[3])));
                searchParams = null;
                sendMessage = makeReply(0, "NEW", id, command[2]);
            }
        }
        return new Reply(sendMessage, true);
    }


    private void updateJobs(Long id, String searchParamId) {
        if (!StringUtils.isNumeric(searchParamId)) {
            jobs = savedJobService.getNewJobsByUserId(id);
        } else {
            jobs = savedJobService.getNewJobsByUserIdAndSearchParams(id,
                    searchParamsService.findById(Long.parseLong(searchParamId)+1));
        }
    }

    private SendMessage makeReply(int index, String state,
                                  Long id, String searchParamsId) {
        SendMessage sendMessage = null;
        if (!jobs.isEmpty() || !searchParams.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            switch (state) {
                case "NEW" -> {
                    stringBuilder.append("New jobs:\n");
                    stringBuilder.append("https://www.linkedin.com/jobs/view/")
                            .append(jobs.get(index).getJobId())
                            .append("\n")
                            .append(index + 1)
                            .append(" of ")
                            .append(jobs.size());
                }
                case "APPLIED" -> {
                    stringBuilder.append("Applied jobs:\n");
                    stringBuilder.append("https://www.linkedin.com/jobs/view/")
                            .append(jobs.get(index).getJobId())
                            .append("\nApplied on - ")
                            .append(new SimpleDateFormat("dd-MMM-yyyy").format(jobs
                                    .get(index).getDateApplied()))
                            .append("\n")
                            .append(index + 1)
                            .append(" of ")
                            .append(jobs.size());
                }
                case "SEARCHES" -> {
                    stringBuilder.append(index + 1).append(" of ").append(searchParams.size())
                            .append("\n").append("\n").append("Keywords: ");
                    for (String keyword : searchParams.get(index).getKeywords()) {
                        stringBuilder.append(keyword).append(" ");
                    }
                    stringBuilder.append("\n");
                    stringBuilder.append("Location: ").append(searchParams.get(index).getLocation()).append("\n\n");
                    stringBuilder.append("Search filters: \n");
                    for (Map.Entry<String, String> entry : searchParams.get(index).getSearchFilters().entrySet()) {
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

                    for (String include : searchParams.get(index).getFilterParams().getIncludeWordsInDescription()) {
                        stringBuilder.append("    ").append(include).append("\n");
                    }
                    stringBuilder.append("\n");
                    stringBuilder.append("  Exclude in description:\n");

                    for (String exclude : searchParams.get(index).getFilterParams().getExcludeWordsFromTitle()) {
                        stringBuilder.append("    ").append(exclude).append("\n");
                    }
                    stringBuilder.append("\n");
                    sendMessage = new SendMessage(id.toString(),
                            stringBuilder.toString());
                }
            }

            sendMessage = new SendMessage(id.toString(),
                    stringBuilder.toString());
            sendMessage.setReplyMarkup(paginationKeyboard.getReplyButtons(
                    index, jobs.size(), state, searchParamsId));
        } else {
            switch (state) {
                case "NEW", "APPLIED" -> sendMessage = new SendMessage(id.toString(), "Nothing " +
                        "left, we will notify if something will appear.\nStay tuned!");

                case "SEARCHES" -> sendMessage = new SendMessage(id.toString(), "No search" +
                        " params found. Add new by pressing /add_search");
            }
        }
        return sendMessage;
    }
}

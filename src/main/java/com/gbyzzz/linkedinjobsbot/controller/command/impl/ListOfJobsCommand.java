package com.gbyzzz.linkedinjobsbot.controller.command.impl;

import com.gbyzzz.linkedinjobsbot.controller.MessageText;
import com.gbyzzz.linkedinjobsbot.controller.command.Command;
import com.gbyzzz.linkedinjobsbot.controller.command.impl.search.AddSearchCommand;
import com.gbyzzz.linkedinjobsbot.controller.command.keyboard.PaginationKeyboard;
import com.gbyzzz.linkedinjobsbot.dto.Reply;
import com.gbyzzz.linkedinjobsbot.entity.SavedJob;
import com.gbyzzz.linkedinjobsbot.entity.SearchParams;
import com.gbyzzz.linkedinjobsbot.entity.UserProfile;
import com.gbyzzz.linkedinjobsbot.service.RedisService;
import com.gbyzzz.linkedinjobsbot.service.SavedJobService;
import com.gbyzzz.linkedinjobsbot.service.SearchParamsService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component(MessageText.WATCH_LIST_OF_JOBS)
@AllArgsConstructor
public class ListOfJobsCommand implements Command {

    private static List<SavedJob> jobs = new ArrayList<>();
    private static List<SearchParams> searchParams = new ArrayList<>();

    private final SavedJobService savedJobService;
    private final PaginationKeyboard paginationKeyboard;
    private final SearchParamsService searchParamsService;
    private final AddSearchCommand addSearchCommand;
    private final RedisService redisService;

    @Override
    public Reply execute(Update update) throws IOException {
        Long id = update.getCallbackQuery().getMessage().getChatId();
        String[] command = update.getCallbackQuery().getData()
                .split(MessageText.BUTTON_VALUE_SEPARATOR);
        SendMessage sendMessage = null;
        Long targetId = null;

        switch (command[1]) {
            case MessageText.NEW -> {
                updateJobs(id, command[2]);
                targetId = jobs.get(Integer.parseInt(command[3])).getJobId();
            }
            case MessageText.APPLIED -> {
                jobs = savedJobService.getAppliedJobsByUserId(id);
                targetId = jobs.get(Integer.parseInt(command[3])).getJobId();
            }
            case MessageText.SEARCHES, MessageText.EDIT ->
                    searchParams = searchParamsService.findAllByUserId(id);
            case MessageText.SCHEDULED -> jobs = savedJobService.getNewJobsByUserId(id);


        }

        switch (command[0]) {
            case MessageText.NEXT, MessageText.PREVIOUS, MessageText.LAST , MessageText.FIRST -> {
                if (command[1].equals(MessageText.NEW) ||
                        command[1].equals(MessageText.APPLIED)) {
                    int index = !command[0].equals(MessageText.LAST) ? Integer.parseInt(command[3]) :
                            jobs.size() - 1;
                    sendMessage = makeReply(index, command[1], id, command[2]);
                    sendMessage.setReplyMarkup(paginationKeyboard.getReplyButtons(
                            index, jobs.size(), command[1], command[2]));
                } else {
                    int index = !command[0].equals(MessageText.LAST) ? Integer.parseInt(command[3]) :
                            searchParams.size() - 1;
                    sendMessage = makeReply(index, command[1], id, command[2]);
                    sendMessage.setReplyMarkup(paginationKeyboard.getReplyButtons(
                            index, searchParams.size(), command[1], command[2]));
                }
            }
            case MessageText.APPLY -> {
                SavedJob savedJob = savedJobService.getJobByIdAndUserId(targetId, id).get();
                savedJobService.saveJob(savedJob);
                savedJob.setReplyState(SavedJob.ReplyState.APPLIED);
                savedJob.setDateApplied(new Date(System.currentTimeMillis()));
                savedJobService.saveJob(savedJob);
                updateJobs(id, command[2]);
                int index = (jobs.size() - 1) > Integer.parseInt(command[3]) ?
                        Integer.parseInt(command[3]) :
                        (Integer.parseInt(command[3]) > 0 ?
                                Integer.parseInt(command[3]) - 1 :
                                Integer.parseInt(command[3]));
                sendMessage = makeReply(index, command[1], id, command[2]);
            }
            case MessageText.DELETE -> {
                switch (command[1]) {
                    case MessageText.NEW, MessageText.APPLIED -> {
                        SavedJob savedJob = savedJobService.getJobByIdAndUserId(targetId, id).get();
                        savedJob.setReplyState(SavedJob.ReplyState.DELETED);
                        savedJobService.saveJob(savedJob);
                        updateJobs(id, command[2]);
                        int index = (jobs.size() - 1) > Integer.parseInt(command[3]) ?
                                Integer.parseInt(command[3]) :
                                (Integer.parseInt(command[3]) > 0 ?
                                        Integer.parseInt(command[3]) - 1 :
                                        Integer.parseInt(command[3]));
                        sendMessage = makeReply(index, command[1], id, command[2]);
                    }
                    case MessageText.SEARCHES -> {
                        int index = (searchParams.size() - 1) > Integer.parseInt(command[3]) ?
                                Integer.parseInt(command[3]) :
                                (Integer.parseInt(command[3]) > 0 ?
                                        Integer.parseInt(command[3]) - 1 :
                                        Integer.parseInt(command[3]));
                        searchParamsService.delete(searchParams.get(Integer.parseInt(command[3])));
                        searchParams = searchParamsService.findAllByUserId(id);
                        jobs = null;
                        sendMessage = makeReply(index, command[1], id, command[2]);
                    }
                }

            }
            case MessageText.REJECTED -> {
                SavedJob savedJob = savedJobService.getJobByIdAndUserId(targetId, id).get();
                savedJobService.saveJob(savedJob);
                savedJob.setReplyState(SavedJob.ReplyState.REJECTED);
                savedJobService.saveJob(savedJob);
                jobs = savedJobService.getAppliedJobsByUserId(id);
                int index = (jobs.size() - 1) > Integer.parseInt(command[3]) ?
                        Integer.parseInt(command[3]) :
                        (Integer.parseInt(command[3]) > 0 ?
                                Integer.parseInt(command[3]) - 1 :
                                Integer.parseInt(command[3]));
                sendMessage = makeReply(index, command[1], id, command[2]);
            }
            case MessageText.NOTIFY -> sendMessage = makeReply(0,
                    UserProfile.BotState.NEW.name(), id, MessageText.ALL);
            case MessageText.RESULTS -> {

                jobs = savedJobService.getNewJobsByUserIdAndSearchParams(id, searchParams
                        .get(Integer.parseInt(command[3])));
                searchParams = null;
                sendMessage = makeReply(0, MessageText.NEW, id, command[3]);
            }
            case MessageText.EDIT -> {
                redisService.saveToTempRepository(searchParams.get(Integer.parseInt(command[3])),
                        update.getCallbackQuery().getMessage().getChatId());
                return addSearchCommand.execute(update);
            }
        }
        return new Reply(sendMessage, true);
    }


    private void updateJobs(Long id, String searchParamId) {
        if (!StringUtils.isNumeric(searchParamId)) {
            jobs = savedJobService.getNewJobsByUserId(id);
        } else {
            searchParams = searchParamsService.findAllByUserId(id);
            jobs = savedJobService.getNewJobsByUserIdAndSearchParams(id,
                    searchParams.get(Integer.parseInt(searchParamId)));
            searchParams = null;
        }
    }

    private SendMessage makeReply(int index, String state,
                                  Long id, String searchParamsId) {
        SendMessage sendMessage = null;
        if ((jobs != null && !jobs.isEmpty()) ||
                (searchParams != null && !searchParams.isEmpty())) {
            switch (state) {
                case MessageText.NEW -> {
                    sendMessage = new SendMessage(id.toString(),
                            MessageText.makeNewJobsReply(index, jobs));
                    sendMessage.setReplyMarkup(paginationKeyboard.getReplyButtons(
                            index, jobs.size(), state, searchParamsId));
                }
                case MessageText.APPLIED -> {
                    sendMessage = new SendMessage(id.toString(),
                            MessageText.makeAppliedJobsReply(index, jobs));
                    sendMessage.setReplyMarkup(paginationKeyboard.getReplyButtons(
                            index, jobs.size(), state, searchParamsId));
                }
                case MessageText.SEARCHES -> {
                    sendMessage = new SendMessage(id.toString(),
                            MessageText.makeSearchReply(index, searchParams));
                    sendMessage.setReplyMarkup(paginationKeyboard.getReplyButtons(
                            index, searchParams.size(), state, searchParamsId));
                }

            }
        } else {
            switch (state) {
                case MessageText.NEW, MessageText.APPLIED -> sendMessage =
                        new SendMessage(id.toString(), MessageText.NO_NEW_OR_APPLIED_JOBS);

                case MessageText.SEARCHES -> sendMessage = new SendMessage(id.toString(),
                        MessageText.NO_SEARCH_PARAMS);
            }
        }
        return sendMessage;
    }
}

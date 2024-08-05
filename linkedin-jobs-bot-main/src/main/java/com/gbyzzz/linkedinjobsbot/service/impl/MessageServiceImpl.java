package com.gbyzzz.linkedinjobsbot.service.impl;

import com.gbyzzz.linkedinjobsbot.controller.MessageText;
import com.gbyzzz.linkedinjobsbot.controller.command.keyboard.PaginationKeyboard;
import com.gbyzzz.linkedinjobsbot.entity.SavedJob;
import com.gbyzzz.linkedinjobsbot.entity.SearchParams;
import com.gbyzzz.linkedinjobsbot.entity.UserProfile;
import com.gbyzzz.linkedinjobsbot.service.MessageService;
import com.gbyzzz.linkedinjobsbot.service.SavedJobService;
import com.gbyzzz.linkedinjobsbot.service.SearchParamsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.text.SimpleDateFormat;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final SavedJobService savedJobService;
    private final SearchParamsService searchParamsService;
    private final PaginationKeyboard paginationKeyboard;

    @Override
    public SendMessage getNewJobByUserId(Long userId, String command) {
        String[] commands = command.split(MessageText.BUTTON_VALUE_SEPARATOR);
        Long searchParamsId = commands[2].equals(MessageText.ALL) ? null : Long.parseLong(commands[2]);
        int page = commands.length > 3 ? Integer.parseInt(commands[3]) : 1;
        Long currentNewJobId = commands.length > 3 ?Long.parseLong(commands[4]) : null;
        SendMessage sendMessage;
        Optional<SavedJob> job = Optional.empty();
        int count = searchParamsId == null ? savedJobService.countSavedJobs(userId, SavedJob.ReplyState.NEW_JOB) :
                savedJobService.countSavedJobs(userId, SavedJob.ReplyState.NEW_JOB, searchParamsId);
        switch (commands[1]) {
            case MessageText.FIRST -> {
                page = 1;
                currentNewJobId = 0L;
                job = searchParamsId == null ?
                        savedJobService.getNextSavedJob(userId, SavedJob.ReplyState.NEW_JOB, currentNewJobId) :
                        savedJobService.getNextSavedJob(userId, SavedJob.ReplyState.NEW_JOB, searchParamsId, currentNewJobId);
            }
            case MessageText.PREVIOUS -> {
                page = page - 1;
                job = searchParamsId == null ?
                        savedJobService.getPrevSavedJob(userId, SavedJob.ReplyState.NEW_JOB, currentNewJobId) :
                        savedJobService.getPrevSavedJob(userId, SavedJob.ReplyState.NEW_JOB, searchParamsId, currentNewJobId);
            }
            case MessageText.NEXT -> {
                page = page + 1;
                job = searchParamsId == null ?
                        savedJobService.getNextSavedJob(userId, SavedJob.ReplyState.NEW_JOB, currentNewJobId) :
                        savedJobService.getNextSavedJob(userId, SavedJob.ReplyState.NEW_JOB, searchParamsId, currentNewJobId);
            }
            case MessageText.LAST -> {
                page = count;
                job = searchParamsId == null ? savedJobService.getLastSavedJob(userId,SavedJob.ReplyState.NEW_JOB) :
                        savedJobService.getLastSavedJob(userId, SavedJob.ReplyState.NEW_JOB, searchParamsId);
            }
        }
        if (!job.isEmpty()) {
            sendMessage = new SendMessage(userId.toString(), makeNewJobsReply(page, job.get(), count));
            sendMessage.setReplyMarkup(paginationKeyboard.getReplyButtons(UserProfile.BotState.NEW.name(),
                    MessageText.ALL, page, count, job.get().getId()));
        } else {
            sendMessage = new SendMessage(userId.toString(), MessageText.GET_NEW_JOBS_REPLY);
        }
        return sendMessage;
    }

    @Override
    public SendMessage getAppliedJobByUserId(Long userId, Long currentAppliedJobId, Long searchParamsId, int page, String direction) {
        SendMessage sendMessage;
        Optional<SavedJob> job = searchParamsId != null ?
                savedJobService.getNextSavedJob(userId, SavedJob.ReplyState.APPLIED, searchParamsId, 0L) :
                savedJobService.getNextSavedJob(userId, SavedJob.ReplyState.APPLIED, 0L);
        int count = savedJobService.countSavedJobs(userId, SavedJob.ReplyState.APPLIED, searchParamsId);
        if (!job.isEmpty()) {
            sendMessage = new SendMessage(userId.toString(), makeAppliedJobsReply(page, job.get(), count));
            sendMessage.setReplyMarkup(paginationKeyboard.getReplyButtons(UserProfile.BotState.NEW.name(),
                    MessageText.ALL, job.get().getId(), count, job.get().getId()));
        } else {
            sendMessage = new SendMessage(userId.toString(), MessageText.GET_NEW_JOBS_REPLY);
        }
        return sendMessage;
    }

    @Override
    public SendMessage getSearchByUserId(Long userId, Long currentSearchParamsId, int page, String direction) {
        SendMessage sendMessage;
        Optional<SearchParams> searchParams = searchParamsService.findPageByUserId(userId, currentSearchParamsId);
        Long count = searchParamsService.getCountByUserId(userId);
        if (!searchParams.isEmpty()) {
            sendMessage = new SendMessage(userId.toString(), makeSearchesReply(page, searchParams.get(), count));
            sendMessage.setReplyMarkup(paginationKeyboard.getReplyButtons(
                    UserProfile.BotState.NEW.name(), MessageText.ALL, page, count, searchParams.get().getId()));
        } else {
            sendMessage = new SendMessage(userId.toString(), MessageText.GET_NEW_JOBS_REPLY);
        }
        return sendMessage;
    }

    private String makeNewJobsReply(int page, SavedJob job, int totalElements) {
        return MessageText.NEW_JOBS +
                MessageText.NEW_LINE +
                MessageText.JOB_URL +
                job.getJobId() +
                MessageText.NEW_LINE +
                page +
                MessageText.OF +
                totalElements;
    }

    private String makeAppliedJobsReply(int page, SavedJob job, int totalElements){
        String date = new SimpleDateFormat(MessageText.DATE_FORMAT)
                .format(job.getDateApplied());

        return MessageText.APPLIED_JOBS + MessageText.NEW_LINE + MessageText.JOB_URL +
                job.getJobId() + MessageText.NEW_LINE +
                MessageText.APPLIED_ON + date +
                MessageText.NEW_LINE + page +
                MessageText.OF + totalElements;
    }

    private String makeSearchesReply(int page, SearchParams searchParams, Long totalElements) {
        return MessageText.NEW_JOBS +
                MessageText.NEW_LINE +
                MessageText.JOB_URL +
                searchParams.getId() +
                MessageText.NEW_LINE +
                page +
                MessageText.OF +
                totalElements;
    }
}

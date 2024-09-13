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
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final SavedJobService savedJobService;
    private final SearchParamsService searchParamsService;
    private final PaginationKeyboard paginationKeyboard;

    @Override
    public SendMessage getNewJobByUserId(Long userId, String[] commands) {
        Long searchParamsId = commands[2].equals(MessageText.ALL) ? null : Long.parseLong(commands[2]);
        int page = commands.length > 3 ? Integer.parseInt(commands[3]) : 1;
        Long currentNewJobId = commands.length > 3 ? Long.parseLong(commands[4]) : null;
        SendMessage sendMessage;
        Optional<SavedJob> job = Optional.empty();
        int count = searchParamsId == null ? savedJobService.countSavedJobs(userId, SavedJob.ReplyState.NEW_JOB) :
                savedJobService.countSavedJobsBySearchParams(SavedJob.ReplyState.NEW_JOB, searchParamsId);
        switch (commands[1]) {
            case MessageText.FIRST -> {
                page = 1;
                currentNewJobId = 0L;
                job = searchParamsId == null ?
                        savedJobService.getNextSavedJob(userId, SavedJob.ReplyState.NEW_JOB, currentNewJobId) :
                        savedJobService.getNextSavedJobBySearchParams(SavedJob.ReplyState.NEW_JOB, searchParamsId, currentNewJobId);
            }
            case MessageText.PREVIOUS -> {
                page = page - 1;
                job = searchParamsId == null ?
                        savedJobService.getPrevSavedJob(userId, SavedJob.ReplyState.NEW_JOB, currentNewJobId) :
                        savedJobService.getPrevSavedJobBySearchParams(SavedJob.ReplyState.NEW_JOB, searchParamsId, currentNewJobId);
            }
            case MessageText.NEXT -> {
                page = page + 1;
                job = searchParamsId == null ?
                        savedJobService.getNextSavedJob(userId, SavedJob.ReplyState.NEW_JOB, currentNewJobId) :
                        savedJobService.getNextSavedJobBySearchParams(SavedJob.ReplyState.NEW_JOB, searchParamsId, currentNewJobId);
            }
            case MessageText.LAST -> {
                page = count;
                job = searchParamsId == null ? savedJobService.getLastSavedJob(userId, SavedJob.ReplyState.NEW_JOB) :
                        savedJobService.getLastSavedJobBySearchParams(SavedJob.ReplyState.NEW_JOB, searchParamsId);
            }
            case MessageText.DELETE -> {
                SavedJob jobToDelete = savedJobService.getJobById(currentNewJobId);
                jobToDelete.setReplyState(SavedJob.ReplyState.DELETED);
                savedJobService.saveJob(jobToDelete);
                count = searchParamsId == null ? savedJobService.countSavedJobs(userId, SavedJob.ReplyState.NEW_JOB) :
                        savedJobService.countSavedJobsBySearchParams(SavedJob.ReplyState.NEW_JOB, searchParamsId);
                if (page < count) {
                    job = searchParamsId == null ?
                            savedJobService.getNextSavedJob(userId, SavedJob.ReplyState.NEW_JOB, currentNewJobId) :
                            savedJobService.getNextSavedJobBySearchParams(SavedJob.ReplyState.NEW_JOB, searchParamsId, currentNewJobId);
                } else {
                    page -= 1;
                    job = searchParamsId == null ?
                            savedJobService.getPrevSavedJob(userId, SavedJob.ReplyState.NEW_JOB, currentNewJobId) :
                            savedJobService.getPrevSavedJobBySearchParams(SavedJob.ReplyState.NEW_JOB, searchParamsId, currentNewJobId);
                }
            }
            case MessageText.APPLY -> {
                SavedJob jobToApply = savedJobService.getJobById(currentNewJobId);
                jobToApply.setReplyState(SavedJob.ReplyState.APPLIED);
                jobToApply.setDateApplied(new Date(System.currentTimeMillis()));
                savedJobService.saveJob(jobToApply);
                count = searchParamsId == null ? savedJobService.countSavedJobs(userId, SavedJob.ReplyState.NEW_JOB) :
                        savedJobService.countSavedJobsBySearchParams(SavedJob.ReplyState.NEW_JOB, searchParamsId);
                if (page < count) {
                    job = searchParamsId == null ?
                            savedJobService.getNextSavedJob(userId, SavedJob.ReplyState.NEW_JOB, currentNewJobId) :
                            savedJobService.getNextSavedJobBySearchParams(SavedJob.ReplyState.NEW_JOB, searchParamsId, currentNewJobId);
                } else {
                    page -= 1;
                    job = searchParamsId == null ?
                            savedJobService.getPrevSavedJob(userId, SavedJob.ReplyState.NEW_JOB, currentNewJobId) :
                            savedJobService.getPrevSavedJobBySearchParams(SavedJob.ReplyState.NEW_JOB, searchParamsId, currentNewJobId);
                }
            }
        }
        if (!job.isEmpty()) {
            sendMessage = new SendMessage(userId.toString(), makeNewJobsReply(page, job.get(), count));
            sendMessage.setReplyMarkup(paginationKeyboard.getReplyButtons(UserProfile.BotState.NEW.name(),
                    commands[2], page, count, job.get().getId()));
        } else {
            sendMessage = new SendMessage(userId.toString(), MessageText.GET_NEW_JOBS_REPLY);
        }
        return sendMessage;
    }

    @Override
    public SendMessage getAppliedJobByUserId(Long userId, String[] commands) {
        Long searchParamsId = commands[2].equals(MessageText.ALL) ? null : Long.parseLong(commands[2]);
        int page = commands.length > 3 ? Integer.parseInt(commands[3]) : 1;
        Long currentNewJobId = commands.length > 3 ? Long.parseLong(commands[4]) : null;
        SendMessage sendMessage;
        Optional<SavedJob> job = Optional.empty();
        int count = searchParamsId == null ? savedJobService.countSavedJobs(userId, SavedJob.ReplyState.APPLIED) :
                savedJobService.countSavedJobsBySearchParams(SavedJob.ReplyState.APPLIED, searchParamsId);
        switch (commands[1]) {
            case MessageText.FIRST -> {
                page = 1;
                currentNewJobId = 0L;
                job = searchParamsId == null ?
                        savedJobService.getNextSavedJob(userId, SavedJob.ReplyState.APPLIED, currentNewJobId) :
                        savedJobService.getNextSavedJobBySearchParams(SavedJob.ReplyState.APPLIED, searchParamsId, currentNewJobId);
            }
            case MessageText.PREVIOUS -> {
                page = page - 1;
                job = searchParamsId == null ?
                        savedJobService.getPrevSavedJob(userId, SavedJob.ReplyState.APPLIED, currentNewJobId) :
                        savedJobService.getPrevSavedJobBySearchParams(SavedJob.ReplyState.APPLIED, searchParamsId, currentNewJobId);
            }
            case MessageText.NEXT -> {
                page = page + 1;
                job = searchParamsId == null ?
                        savedJobService.getNextSavedJob(userId, SavedJob.ReplyState.APPLIED, currentNewJobId) :
                        savedJobService.getNextSavedJobBySearchParams(SavedJob.ReplyState.APPLIED, searchParamsId, currentNewJobId);
            }
            case MessageText.LAST -> {
                page = count;
                job = searchParamsId == null ? savedJobService.getLastSavedJob(userId, SavedJob.ReplyState.APPLIED) :
                        savedJobService.getLastSavedJobBySearchParams(SavedJob.ReplyState.APPLIED, searchParamsId);
            }
            case MessageText.REJECTED -> {
                SavedJob jobToApply = savedJobService.getJobById(currentNewJobId);
                jobToApply.setReplyState(SavedJob.ReplyState.REJECTED);
                savedJobService.saveJob(jobToApply);
                count = searchParamsId == null ? savedJobService.countSavedJobs(userId, SavedJob.ReplyState.APPLIED) :
                        savedJobService.countSavedJobsBySearchParams(SavedJob.ReplyState.APPLIED, searchParamsId);
                if (page < count) {
                    job = searchParamsId == null ?
                            savedJobService.getNextSavedJob(userId, SavedJob.ReplyState.APPLIED, currentNewJobId) :
                            savedJobService.getNextSavedJobBySearchParams(SavedJob.ReplyState.APPLIED, searchParamsId, currentNewJobId);
                } else {
                    page -= 1;
                    job = searchParamsId == null ?
                            savedJobService.getPrevSavedJob(userId, SavedJob.ReplyState.APPLIED, currentNewJobId) :
                            savedJobService.getPrevSavedJobBySearchParams(SavedJob.ReplyState.APPLIED, searchParamsId, currentNewJobId);
                }
            }

        }
        if (!job.isEmpty()) {
            sendMessage = new SendMessage(userId.toString(), makeAppliedJobsReply(page, job.get(), count));
            sendMessage.setReplyMarkup(paginationKeyboard.getReplyButtons(UserProfile.BotState.APPLIED.name(),
                    MessageText.ALL, page, count, job.get().getId()));
        } else {
            sendMessage = new SendMessage(userId.toString(), MessageText.GET_APPLIED_JOBS_REPLY);
        }
        return sendMessage;
    }

    @Override
    public SendMessage getSearchByUserId(Long userId, String[] commands) {

        int page = commands.length > 3 ? Integer.parseInt(commands[3]) : 1;
        Long currentSearchParamsId = commands.length > 3 ? Long.parseLong(commands[4]) : null;
        Optional<SearchParams> searchParams = Optional.empty();
        int count = searchParamsService.getCountByUserId(userId);
        SendMessage sendMessage = null;
        switch (commands[1]) {
            case MessageText.FIRST -> {
                page = 1;
                currentSearchParamsId = 0L;
                searchParams = searchParamsService.findNextSearchParams(userId, 0L);
            }
            case MessageText.PREVIOUS -> {
                page = page - 1;
                searchParams = searchParamsService.findPreviousSearchParams(userId, currentSearchParamsId);
            }
            case MessageText.NEXT -> {
                page = page + 1;
                searchParams = searchParamsService.findNextSearchParams(userId, currentSearchParamsId);
            }
            case MessageText.LAST -> {
                page = count;
                searchParams = searchParamsService.findLastSearchParams(userId);
            }
            case MessageText.DELETE -> {
                searchParamsService.deleteById(currentSearchParamsId);
                count = searchParamsService.getCountByUserId(userId);
                if(page < count) {
                    searchParams = searchParamsService.findNextSearchParams(userId, currentSearchParamsId);
                } else {
                    page = page - 1;
                    searchParams = searchParamsService.findPreviousSearchParams(userId, currentSearchParamsId);
                }
            }
            case MessageText.RESULTS -> {
                sendMessage = getNewJobByUserId(userId, new String[]{MessageText.NEW, MessageText.FIRST, currentSearchParamsId.toString()});
            }
        }

        if (!searchParams.isEmpty()) {
            sendMessage = new SendMessage(userId.toString(), makeSearchesReply(page, searchParams.get(), count));
            sendMessage.setReplyMarkup(paginationKeyboard.getReplyButtons(
                    UserProfile.BotState.SEARCHES.name(), searchParams.get().getId().toString(), page, count, searchParams.get().getId()));
        } else  if (sendMessage == null){
            sendMessage = new SendMessage(userId.toString(), MessageText.GET_ALL_SEARCHES_REPLY);
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

    private String makeAppliedJobsReply(int page, SavedJob job, int totalElements) {
        String date = new SimpleDateFormat(MessageText.DATE_FORMAT)
                .format(job.getDateApplied());

        return MessageText.APPLIED_JOBS + MessageText.NEW_LINE + MessageText.JOB_URL +
                job.getJobId() + MessageText.NEW_LINE +
                MessageText.APPLIED_ON + date +
                MessageText.NEW_LINE + page +
                MessageText.OF + totalElements;
    }

    private String makeSearchesReply(int index, SearchParams searchParams, int totalElements) {
        StringBuilder reply = new StringBuilder();
        reply.append(index).append(MessageText.OF).append(totalElements)
                .append(MessageText.NEW_LINE)
                .append(MessageText.NEW_LINE)
                .append(MessageText.KEYWORDS)
                .append(MessageText.SPACE);
        for (String keyword : searchParams.getKeywords()) {
            reply.append(keyword).append(MessageText.SPACE);
        }
        reply.append(MessageText.NEW_LINE);
        reply.append(MessageText.LOCATION).append(MessageText.SPACE)
                .append(searchParams.getLocation())
                .append(MessageText.NEW_LINE)
                .append(MessageText.NEW_LINE);
        reply.append(MessageText.SEARCH_FILTERS)
                .append(MessageText.SPACE)
                .append(MessageText.NEW_LINE);
        for (Map.Entry<String, String> entry : searchParams.getSearchFilters().entrySet()) {
            reply.append(MessageText.TWO_SPACES);
            reply.append(entry.getKey())
                    .append(MessageText.COLON)
                    .append(MessageText.NEW_LINE);

            switch (entry.getKey()) {
                case MessageText.WORKPLACE_TYPE -> {
                    for (String val : entry.getValue().split(MessageText.COMMA)) {
                        reply.append(MessageText.FOUR_SPACES);
                        reply.append(Workplace.getName(Integer.parseInt(val)));
                        reply.append(MessageText.SPACE);
                    }
                }
                case MessageText.EXPERIENCE -> {
                    for (String val : entry.getValue().split(MessageText.COMMA)) {
                        reply.append(MessageText.FOUR_SPACES);
                        reply.append(Experience.getName(val));
                        reply.append(MessageText.SPACE);
                    }
                }
                case MessageText.JOB_TYPE -> {
                    for (String val : entry.getValue().split(MessageText.COMMA)) {
                        reply.append(MessageText.FOUR_SPACES);
                        reply.append(JobTypes.getName(val));
                        reply.append(MessageText.SPACE);
                    }
                }
            }
            reply.append(MessageText.NEW_LINE);
        }
        reply.append(MessageText.ADDITIONAL_FILTERS)
                .append(MessageText.SPACE)
                .append(MessageText.NEW_LINE);

        reply.append(MessageText.TWO_SPACES)
                .append(MessageText.INCLUDE_IN_DESCRIPTION)
                .append(MessageText.NEW_LINE);

        for (String include : searchParams.getFilterParams()
                .getIncludeWordsInDescription()) {
            reply.append(MessageText.FOUR_SPACES)
                    .append(include)
                    .append(MessageText.SPACE);
        }
        reply.append(MessageText.NEW_LINE);
        reply.append(MessageText.TWO_SPACES)
                .append(MessageText.EXCLUDE_FROM_TITLE)
                .append(MessageText.NEW_LINE);

        for (String exclude : searchParams.getFilterParams()
                .getExcludeWordsFromTitle()) {
            reply.append(MessageText.FOUR_SPACES)
                    .append(exclude)
                    .append(MessageText.SPACE);
        }
        reply.append(MessageText.NEW_LINE);

        return reply.toString();
    }
}

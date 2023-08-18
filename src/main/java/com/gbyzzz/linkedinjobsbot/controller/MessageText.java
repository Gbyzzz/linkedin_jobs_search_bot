package com.gbyzzz.linkedinjobsbot.controller;

import com.gbyzzz.linkedinjobsbot.entity.SavedJob;
import com.gbyzzz.linkedinjobsbot.entity.SearchParams;
import com.gbyzzz.linkedinjobsbot.service.impl.Experience;
import com.gbyzzz.linkedinjobsbot.service.impl.JobTypes;
import com.gbyzzz.linkedinjobsbot.service.impl.Workplace;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@Component
public abstract class MessageText {
    public static final String NEXT = "next";
    public static final String PREVIOUS = "previous";
    public static final String APPLY = "apply";
    public static final String DELETE = "delete";
    public static final String REJECTED = "rejected";
    public static final String NOTIFY = "notify";
    public static final String RESULTS = "results";
    public static final String NEW = "NEW";
    public static final String APPLIED = "APPLIED";
    public static final String SEARCHES = "SEARCHES";
    public static final String SCHEDULED = "SCHEDULED";
    public static final String ZERO = "0";
    public static final String ONE = "1";
    public static final String OF = " of ";
    public static final String EXIT = "Exit";
    public static final String ALL = "ALL";
    public static final String DATE_FORMAT = "dd-MMM-yyyy";
    public static final String SPACE = " ";
    public static final String TWO_SPACES = "  ";
    public static final String FOUR_SPACES = "    ";
    public static final String NEW_LINE = "\n";
    public static final String NEW_JOBS = "New jobs:";
    public static final String APPLIED_JOBS = "Applied jobs:";
    public static final String EXPERIENCE = "experience";
    public static final String JOB_TYPE = "jobType";
    public static final String WORKPLACE_TYPE = "workplaceType";
    public static final String JOB_URL = "https://www.linkedin.com/jobs/view/";
    public static final String APPLIED_ON = "Applied on - ";
    public static final String COLON = ":";
    public static final String COMMA = ",";

    public static final String ADD_EXPERIENCE = "ADD_EXPERIENCE";
    public static final String ADD_JOB_TYPE = "ADD_JOB_TYPE";
    public static final String ADD_KEYWORDS = "ADD_KEYWORDS";
    public static final String ADD_LOCATION = "ADD_LOCATION";
    public static final String ADD_SEARCH = "ADD_SEARCH";
    public static final String ADD_WORKPLACE = "ADD_WORKPLACE";
    public static final String ADD_FILTER_INCLUDE = "ADD_FILTER_INCLUDE";
    public static final String ADD_FILTER_EXCLUDE = "ADD_FILTER_EXCLUDE";
    public static final String GET_ALL_SEARCHES = "GET_ALL_SEARCHES";
    public static final String GET_APPLIED_JOBS = "GET_APPLIED_JOBS";
    public static final String GET_NEW_JOBS = "GET_NEW_JOBS";
    public static final String WATCH_LIST_OF_JOBS = "WATCH_LIST_OF_JOBS";
    public static final String MAKE_FIRST_SEARCH = "MAKE_FIRST_SEARCH";
    public static final String START = "START";
    public static final String MAIN_MENU = "MAIN_MENU";
    public static final String WRONG = "WRONG";

    public static final String ADD_EXPERIENCE_REPLY  = "Search parameters added\nNow you can add " +
            "additional search parameters, experience:";
    public static final String ADD_EXPERIENCE_REPLY_NEXT = "Now add job type:";
    public static final String ADD_JOB_TYPE_REPLY = "Now add job type:";
    public static final String ADD_JOB_TYPE_REPLY_NEXT = "Now add workplace type:";
    public static final String INPUTTED_KEYWORDS = "Your keywords are:";
    public static final String ENTER_LOCATION = "Please enter Location";
    public static final String ADD_LOCATION_REPLY = "Search parameters added\n Now you can add " +
            "additional search parameters, experience :";
    public static final String ADD_SEARCH_REPLY = "Enter keywords = separate them by space) or " +
            "/main_menu to return to main menu";
    public static final String ADD_WORKPLACE_REPLY = "Enter keywords that should be included = " +
            "separate them by space)";
    public static final String GET_APPLIED_JOBS_REPLY = "No new jobs at the moment, if " +
            "something comes up we will notice you";
    public static final String GET_ALL_SEARCHES_REPLY = "No search params found, please add one " +
            "with /add_search";
    public static final String KEYWORDS = "Keywords:";
    public static final String LOCATION = "Location:";
    public static final String SEARCH_FILTERS = "Search filters:";
    public static final String ADDITIONAL_FILTERS = "Additional filters:";
    public static final String INCLUDE_IN_DESCRIPTION = "Include in description:";
    public static final String EXCLUDE_FROM_TITLE = "Exclude from title:";
    public static final String GET_NEW_JOBS_REPLY = "No new jobs at the moment, if something " +
            "comes up we will notice you";
    public static final String NO_NEW_OR_APPLIED_JOBS = "Nothing left, we will notify if" +
            " something will appear.\nStay tuned!";
    public static final String NO_SEARCH_PARAMS = "No search params found. Add new by pressing" +
            " /add_search";
    public static final String WRONG_REPLY = "Wrong select";
    public static final String MAIN_MENU_REPLY = "Input /add_search to add search to you account";
    public static final String START_REPLY = "\uD83D\uDE80 Starting \uD83D\uDE80 \n " +
            "Input /add_search to add search to you account";
    public static final String START_REPLY_ALREADY_STARTED = "\uD83D\uDE80 Starting \uD83D\uDE80" +
            " \n Input /add_search to add search to you account";
    public static final String MAKE_FIRST_SEARCH_NO_RESULTS = "Nothing was found. Please check " +
            "your search params or wait, maybe something will come up";
    public static final String MAKE_FIRST_SEARCH_PARAMS_ALREADY_EXISTS = "You already have these" +
            " search params";
    public static final String ADD_FILTER_EXCLUDE_REPLY = "To start first scan please input" +
            " /make_first_search";
    public static final String ADD_FILTER_INCLUDE_REPLY_START = "Your keywords, that should" +
            " be included are:\n";
    public static final String ADD_FILTER_INCLUDE_REPLY_END = "\nPlease enter keywords, that" +
            " shouldn't be in the results(separate them by space):";



    public static String makeSearchReply(int index, List<SearchParams> searchParams) {
        StringBuilder reply = new StringBuilder();
        reply.append(index + 1).append(MessageText.OF).append(searchParams.size())
                .append(MessageText.NEW_LINE)
                .append(MessageText.NEW_LINE)
                .append(MessageText.KEYWORDS)
                .append(MessageText.SPACE);
        for (String keyword : searchParams.get(index).getKeywords()) {
            reply.append(keyword).append(MessageText.SPACE);
        }
        reply.append(MessageText.NEW_LINE);
        reply.append(MessageText.LOCATION).append(MessageText.SPACE)
                .append(searchParams.get(index).getLocation())
                .append(MessageText.NEW_LINE)
                .append(MessageText.NEW_LINE);
        reply.append(MessageText.SEARCH_FILTERS)
                .append(MessageText.SPACE)
                .append(MessageText.NEW_LINE);
        for (Map.Entry<String, String> entry : searchParams.get(index)
                .getSearchFilters().entrySet()) {
            reply.append(MessageText.TWO_SPACES);
            reply.append(entry.getKey())
                    .append(MessageText.COLON)
                    .append(MessageText.NEW_LINE);

            switch (entry.getKey()) {
                case MessageText.WORKPLACE_TYPE -> {
                    for (String val : entry.getValue().split(MessageText.COMMA)) {
                        reply.append(MessageText.FOUR_SPACES);
                        reply.append(Workplace.getName(Integer.parseInt(val)));
                        reply.append(MessageText.NEW_LINE);
                    }
                }
                case MessageText.EXPERIENCE -> {
                    for (String val : entry.getValue().split(MessageText.COMMA)) {
                        reply.append(MessageText.FOUR_SPACES);
                        reply.append(Experience.getName(val));
                        reply.append(MessageText.NEW_LINE);
                    }
                }
                case MessageText.JOB_TYPE -> {
                    for (String val : entry.getValue().split(MessageText.COMMA)) {
                        reply.append(MessageText.FOUR_SPACES);
                        reply.append(JobTypes.getName(val));
                        reply.append(MessageText.NEW_LINE);
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

        for (String include : searchParams.get(index).getFilterParams()
                .getIncludeWordsInDescription()) {
            reply.append(MessageText.FOUR_SPACES)
                    .append(include)
                    .append(MessageText.NEW_LINE);
        }
        reply.append(MessageText.NEW_LINE);
        reply.append(MessageText.TWO_SPACES)
                .append(MessageText.EXCLUDE_FROM_TITLE)
                .append(MessageText.NEW_LINE);

        for (String exclude : searchParams.get(index).getFilterParams()
                .getExcludeWordsFromTitle()) {
            reply.append(MessageText.FOUR_SPACES)
                    .append(exclude)
                    .append(MessageText.NEW_LINE);
        }
        reply.append(MessageText.NEW_LINE);

        return reply.toString();
    }

    public static String makeAppliedJobsReply(int index, List<SavedJob> jobs){
        String date = new SimpleDateFormat(MessageText.DATE_FORMAT)
                .format(jobs.get(index).getDateApplied());

        return MessageText.APPLIED_JOBS + MessageText.NEW_LINE + MessageText.JOB_URL +
                jobs.get(index).getJobId() + MessageText.NEW_LINE +
                MessageText.APPLIED_ON + date +
                MessageText.NEW_LINE + (index + 1) +
                MessageText.OF + jobs.size();
    }

    public static String makeNewJobsReply(int index, List<SavedJob> jobs) {
        return MessageText.NEW_JOBS +
                MessageText.NEW_LINE +
                MessageText.JOB_URL +
                jobs.get(index).getJobId() +
                MessageText.NEW_LINE +
                (index + 1) +
                MessageText.OF +
                jobs.size();
    }
}

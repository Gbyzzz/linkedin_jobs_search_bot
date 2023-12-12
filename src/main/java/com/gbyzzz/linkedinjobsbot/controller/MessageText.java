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
    public static final String NA = "na";
    public static final String FIRST = "first";
    public static final String NEXT = "next";
    public static final String LAST = "last";
    public static final String PREVIOUS = "previous";
    public static final String APPLY = "apply";
    public static final String DELETE = "delete";
    public static final String EDIT = "edit";
    public static final String REJECTED = "rejected";
    public static final String NOTIFY = "notify";
    public static final String RESULTS = "results";
    public static final String NEW = "NEW";
    public static final String APPLIED = "APPLIED";
    public static final String SEARCHES = "SEARCHES";
    public static final String SCHEDULED = "SCHEDULED";
    public static final String ZERO = "0";
    public static final String ONE = "1";
    public static final String PLUS = "+";
    public static final String OF = " of ";
    public static final String EXIT = "Your account have been deleted";
    public static final String ALL = "ALL";
    public static final String DATE_FORMAT = "dd-MMM-yyyy";
    public static final String SLASH = "/";
    public static final String OPPOSITE_SLASH = "\\";
    public static final String ALL_WORD_REGEX = ".*";
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

    public static final String NO_ACCOUNT = "NO_ACCOUNT";
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
    public static final String CANCEL_EDITING = "CANCEL_EDITING";

    public static final String BUTTON_DISABLED = "➖";
    public static final String BUTTON_FIRST = "First";
    public static final String BUTTON_FIRST_ARROW = "⏪";
    public static final String BUTTON_LAST = "Last";
    public static final String BUTTON_LAST_ARROW = "⏩";
    public static final String BUTTON_NEXT = "Next";
    public static final String BUTTON_NEXT_ARROW = "▶\uFE0F";
    public static final String BUTTON_PREVIOUS = "Previous";
    public static final String BUTTON_PREVIOUS_ARROW = "◀\uFE0F";
    public static final String BUTTON_START = "/start";
    public static final String BUTTON_ADD_SEARCH = "/add_search";
    public static final String BUTTON_GET_ALL_SEARCHES = "/get_all_searches";
    public static final String BUTTON_GET_APPLIED_JOBS = "/get_applied_jobs";
    public static final String BUTTON_GET_NEW_JOBS = "/get_new_jobs";
    public static final String BUTTON_MAIN_MENU = "/main";
    public static final String BUTTON_YES_CHECK_BOX = "✅";
    public static final String BUTTON_NO_CHECK_BOX = "❌";
    public static final String BUTTON_EDIT_EMOJI = "📝";
    public static final String BUTTON_EDIT = "Edit";
    public static final String BUTTON_INTERNSHIP = "Internship";
    public static final String BUTTON_INTERNSHIP_VALUE = "toggle_internship";
    public static final String BUTTON_ENTRY_LEVEL = "Entry Level";
    public static final String BUTTON_ENTRY_LEVEL_VALUE = "toggle_entry";
    public static final String BUTTON_ASSOCIATE = "Associate";
    public static final String BUTTON_ASSOCIATE_VALUE = "toggle_associate";
    public static final String BUTTON_MID_SENIOR_LEVEL = "Mid-Senior Level";
    public static final String BUTTON_MID_SENIOR_LEVEL_VALUE = "toggle_mid_senior";
    public static final String BUTTON_DIRECTOR = "Director";
    public static final String BUTTON_DIRECTOR_VALUE = "toggle_director";
    public static final String BUTTON_EXECUTIVE = "Executive";
    public static final String BUTTON_ALL = "All";
    public static final String BUTTON_EXECUTIVE_VALUE = "toggle_executive";
    public static final String BUTTON_ALL_EXPERIENCE = "toggle_all_experience";
    public static final String BUTTON_ALL_JOB_TYPE = "toggle_all_job_type";
    public static final String BUTTON_ALL_WORKPLACE = "toggle_all_workplace";
    public static final String BUTTON_FULL_TIME = "Full-time";
    public static final String BUTTON_FULL_TIME_VALUE = "toggle_full_time";
    public static final String BUTTON_PART_TIME = "Part-time";
    public static final String BUTTON_PART_TIME_VALUE = "toggle_part_time";
    public static final String BUTTON_CONTRACT = "Contract";
    public static final String BUTTON_CONTRACT_VALUE = "toggle_contract";
    public static final String BUTTON_TEMPORARY = "Temporary";
    public static final String BUTTON_TEMPORARY_VALUE = "toggle_temporary";
    public static final String BUTTON_ISRAEL = "Israel";
    public static final String BUTTON_TEL_AVIV = "Tel-Aviv";
    public static final String BUTTON_TEL_AVIV_VALUE = "Tel_Aviv";
    public static final String BUTTON_HAIFA = "Haifa";
    public static final String BUTTON_JERUSALEM = "Jerusalem";
    public static final String BUTTON_USA = "USA";
    public static final String BUTTON_ON_SITE = "On-site";
    public static final String BUTTON_ON_SITE_VALUE = "toggle_on_site";
    public static final String BUTTON_REMOTE = "Remote";
    public static final String BUTTON_REMOTE_VALUE = "toggle_remote";
    public static final String BUTTON_HYBRID = "Hybrid";
    public static final String BUTTON_HYBRID_VALUE = "toggle_hybrid";
    public static final String BUTTON_DELETE = "Delete";
    public static final String BUTTON_REJECTED = "Rejected";
    public static final String BUTTON_RESULTS = "Results(new)";
    public static final String BUTTON_RESULTS_EMOJI = "\uD83D\uDCCA";
    public static final String BUTTON_APPLIED = "Applied";
    public static final String BUTTON_VALUE_SEPARATOR = "_";
    public static final String LOCATION_EDIT = "Preciously inputted location:\n";
    public static final String NO_ACCOUNT_REPLY = "You don't have an account, please input /start" +
            " to add";
    public static final String ADD_EXPERIENCE_REPLY  = "Search parameters added\nNow you can add " +
            "additional search parameters, experience:";
    public static final String ADD_EXPERIENCE_REPLY_NEXT = "Now add job type:";
    public static final String ADD_JOB_TYPE_REPLY = "Now add job type:";
    public static final String ADD_JOB_TYPE_REPLY_NEXT = "Now add workplace type:";
    public static final String INPUTTED_KEYWORDS = "Your keywords are:";
    public static final String ENTER_LOCATION = "Please choose location:";
    public static final String ADD_LOCATION_REPLY = "Search parameters added\nNow you can add " +
            "additional search parameters, experience:";
    public static final String ADD_SEARCH_REPLY = "Enter keywords (separate them by space) or " +
            "/main to return to main menu";
    public static final String EXCLUDE_EDIT_START = "Editing words, that should be excluded from" +
            " job title. The words that you have inputted before are:\n";
    public static final String ADD_WORKPLACE_REPLY = "Enter keywords that should be included" +
            "(separate them by space)";
    public static final String INCLUDE_EDIT_START = "\nPreviously inputted words:\n";
    public static final String TEXT_INPUT_EDIT_END = "\nIf you don't want to change anything, " +
            "input plus sign - '+'";
    public static final String CANCEL_EDITING_COMMAND = "\nTo cancel editing input /cancel\n";
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
    public static final String EDIT_KEYWORDS_REPLY = "Editing search parameters:\n" +
            "  Keywords:\n";
    public static final String WRONG_REPLY = "Wrong select";
    public static final String MAIN_MENU_REPLY = "Commands:\n\n" +
            "\uD83D\uDD38 /add_search - to add search to you account\n" +
            "\uD83D\uDD38 /get_all_searches - to show all your search parameters\n" +
            "\uD83D\uDD38 /get_applied_jobs - to show jobs which you have applied\n" +
            "\uD83D\uDD38 /get_new_jobs - to show all new jobs according to all your search" +
            " parameters.\n" +
            "\uD83D\uDD39 To get new jobs of the particular search parameters go to " +
            "/get_all_searches navigate to desired search parameters and press \"Results(new)\"" +
            " button.\n\uD83D\uDD38 /exit - to delete your account from bot with all data";
    public static final String START_REPLY = "\uD83D\uDE80 Starting \uD83D\uDE80 \n";
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


    public static final String URL_SEARCH_JOBS_START = "https://www.linkedin.com/voyager/api/" +
            "voyagerJobsDashJobCards?decorationId=com.linkedin.voyager.dash.deco.jobs.search." +
            "JobSearchCardsCollection-169&count=100&q=jobSearch&query=(origin:" +
            "JOB_SEARCH_PAGE_JOB_FILTER,spellCorrectionEnabled:true,";
    public static final String URL_SEARCH_JOBS_END = ")&start=";
    public static final String GET_JOB_START = "https://www.linkedin.com/voyager/api/jobs/jobPostings/";
    public static final String GET_JOB_END = "?decorationId=com.linkedin.voyager.deco.jobs.web" +
            ".shared.WebFullJobPosting-65&topNRequestedFlavors=List(TOP_APPLICANT,IN_NETWORK," +
            "COMPANY_RECRUIT,SCHOOL_RECRUIT,HIDDEN_GEM,ACTIVELY_HIRING_COMPANY)";
    public static final String JOB_SEARCH_QUERY_KEYWORDS = "keywords:";
    public static final String JOB_SEARCH_QUERY_KEYWORDS_SEPARATOR = "%20";
    public static final String JOB_SEARCH_QUERY_LOCATION_START = "locationUnion:(geoId:";
    public static final String JOB_SEARCH_QUERY_END = "),";
    public static final String JOB_SEARCH_QUERY_FILTERS = "selectedFilters:(";
    public static final String JOB_SEARCH_QUERY_LIST = ":List(";
    public static final String JOB_SEARCH_QUERY_TIME_RANGE = "timePostedRange:List(r";
    public static final String ID = "id=";
    public static final String COOKIE = "Cookie";
    public static final String CSRF_TOKEN = "Csrf-Token";
    public static final String GET = "GET";
    public static final String JSON_NODE_METADATA = "metadata";
    public static final String JSON_NODE_JOB_CARD_PREFETCH_QUERIES = "jobCardPrefetchQueries";
    public static final String JSON_NODE_PREFETCH_JOB_POSTING_CARD_URNS = "prefetchJobPostingCardUrns";
    public static final String NON_NUMERIC = "[^0-9]";
    public static final String EMPTY = "";
    public static final String PAGING = "paging";
    public static final String TOTAL = "total";
    public static final String COUNT_0 = "count=0";
    public static final String COUNT_100 = "count=100";
    public static final String INCLUDE_REGEX_START = "\\b(?:";
    public static final String INCLUDE_REGEX_END = ")\\b";
    public static final String EXCLUDE_REGEX_START = "^(?:(?!";
    public static final String EXCLUDE_REGEX_END = ").)*$\\r?\\n?";
    public static final String REGEX_SEPARATOR = "|";
    public static final String JOBS_TASK_EXECUTOR = "jobsTaskExecutor";
    public static final String SEARCH_TASK_EXECUTOR = "searchTaskExecutor";
    public static final String ERROR = "Error: ";




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

        for (String include : searchParams.get(index).getFilterParams()
                .getIncludeWordsInDescription()) {
            reply.append(MessageText.FOUR_SPACES)
                    .append(include)
                    .append(MessageText.SPACE);
        }
        reply.append(MessageText.NEW_LINE);
        reply.append(MessageText.TWO_SPACES)
                .append(MessageText.EXCLUDE_FROM_TITLE)
                .append(MessageText.NEW_LINE);

        for (String exclude : searchParams.get(index).getFilterParams()
                .getExcludeWordsFromTitle()) {
            reply.append(MessageText.FOUR_SPACES)
                    .append(exclude)
                    .append(MessageText.SPACE);
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

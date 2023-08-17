package com.gbyzzz.linkedinjobsbot.controller;

public enum MessageText {
    NEXT("next"),
    PREVIOUS("previous"),
    ONE("1"),
    OF(" of "),
    EXIT("Exit"),
    ALL("ALL"),
    DATE_FORMAT("dd-MMM-yyyy"),
    SPACE(" "),
    NEW_LINE("\n"),
    NEW_JOBS("New jobs:"),
    EXPERIENCE("experience"),
    JOB_TYPE("jobType"),
    WORKPLACE_TYPE("workplaceType"),
    JOB_URL("https://www.linkedin.com/jobs/view/"),
    APPLIED_ON("Applied on - "),
    ADD_EXPERIENCE_REPLY ("Search parameters added\nNow you can add additional search parameters," +
            " experience:"),
    ADD_EXPERIENCE_REPLY_NEXT("Now add job type:"),
    ADD_JOB_TYPE_REPLY("Now add job type:"),
    ADD_JOB_TYPE_REPLY_NEXT("Now add workplace type:"),
    INPUTTED_KEYWORDS("Your keywords are:"),
    ENTER_LOCATION("Please enter Location"),
    ADD_LOCATION_REPLY("Search parameters added\n Now you can add additional search parameters," +
            " experience :"),
    ADD_SEARCH_REPLY("Enter keywords(separate them by space) or /main_menu to return to main menu"),
    ADD_WORKPLACE_REPLY("Enter keywords that should be included(separate them by space)"),
    GET_APPLIED_JOBS_REPLY("No new jobs at the moment, if something comes up we will notice you");


    private final String text;

    MessageText(String text) {
        this.text = text;
    }

    public String getValue(){
        return text;
    }
}

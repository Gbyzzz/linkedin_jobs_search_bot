package com.gbyzzz.linkedinjobsbot.controller;

import com.gbyzzz.linkedinjobsbot.entity.UserProfile;

import java.util.Objects;

public enum CommandName {

    START ("/start", null),
    EXIT ("/exit", null),
    ADD_SEARCH("/add_search", null),
    MAIN_MENU("/main_menu", null),
    MAKE_SEARCH("/make_search", null),
    ADD_KEYWORDS(UserProfile.BotState.ADD_KEYWORDS.name(), null),
    ADD_LOCATION(UserProfile.BotState.ADD_LOCATION.name(), null),
    ADD_EXPERIENCE(UserProfile.BotState.ADD_EXPERIENCE.name(), null),
    ADD_JOB_TYPE(UserProfile.BotState.ADD_JOB_TYPE.name(), null),
    ADD_WORKPLACE(UserProfile.BotState.ADD_WORKPLACE.name(), null),
    ADD_FILTER_EXCLUDE(UserProfile.BotState.ADD_FILTER_EXCLUDE.name(), null),
    ADD_FILTER_INCLUDE(UserProfile.BotState.ADD_FILTER_INCLUDE.name(), null),
    ADD_FILTER_JOB_TYPE(UserProfile.BotState.ADD_FILTER_JOB_TYPE.name(), null),
    ADD_FILTERS(UserProfile.BotState.ADD_FILTERS.name(), null),
    ADD_FILTER_WORKPLACE(UserProfile.BotState.ADD_FILTER_WORKPLACE.name(), null),
    WATCH_LIST_OF_JOBS(UserProfile.BotState.LIST_NEW_JOBS.name(),
            UserProfile.BotState.LIST_APPLIED_JOBS.name()),
    MAKE_FIRST_SEARCH("/make_first_search", null),
    GET_APPLIED_JOBS("/get_applied_jobs", null),
    GET_NEW_JOBS("/get_new_jobs", null);

    private final String firstValue;
    private final String secondValue;

    CommandName(final String firstValue, final String secondValue) {
        this.firstValue = firstValue;
        this.secondValue = secondValue;
    }

    static CommandName getValue(final String val) {
        for (CommandName e: CommandName.values()) {
            if (Objects.equals(e.firstValue, val) || Objects.equals(e.secondValue, val)) {
                return e;
            }
        }
        return null;
    }
}

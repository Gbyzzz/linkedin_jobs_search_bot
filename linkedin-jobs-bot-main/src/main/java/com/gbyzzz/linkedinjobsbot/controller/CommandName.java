package com.gbyzzz.linkedinjobsbot.controller;


import com.gbyzzz.linkedinjobsbot.modules.commons.values.MessageText;
import com.gbyzzz.linkedinjobsbot.modules.postgresdb.entity.UserProfile;

import java.util.ArrayList;
import java.util.List;

public enum CommandName {

    START (new ArrayList<>(){{add("/start");}}),
    NO_ACCOUNT(new ArrayList<>(){{add(MessageText.NO_ACCOUNT);}}),
    EXIT (new ArrayList<>(){{add("/exit");}}),
    ADD_SEARCH(new ArrayList<>(){{add("/add_search");}}),
    MAIN_MENU(new ArrayList<>(){{add("/main");}}),
    MAKE_SEARCH(new ArrayList<>(){{add("/make_search");}}),
    CANCEL_EDITING(new ArrayList<>(){{add("/cancel");}}),
    ADD_KEYWORDS(new ArrayList<>(){{add(UserProfile.BotState.ADD_KEYWORDS.name());}}),
    ADD_LOCATION(new ArrayList<>(){{add(UserProfile.BotState.ADD_LOCATION.name());}}),
    ADD_EXPERIENCE(new ArrayList<>(){{add(UserProfile.BotState.ADD_EXPERIENCE.name());}}),
    ADD_JOB_TYPE(new ArrayList<>(){{add(UserProfile.BotState.ADD_JOB_TYPE.name());}}),
    ADD_WORKPLACE(new ArrayList<>(){{add(UserProfile.BotState.ADD_WORKPLACE.name());}}),
    ADD_FILTER_EXCLUDE(new ArrayList<>(){{add(UserProfile.BotState.ADD_FILTER_EXCLUDE.name());}}),
    ADD_FILTER_INCLUDE(new ArrayList<>(){{add(UserProfile.BotState.ADD_FILTER_INCLUDE.name());}}),
    ADD_FILTER_JOB_TYPE(new ArrayList<>(){{add(UserProfile.BotState.ADD_FILTER_JOB_TYPE.name());}}),
    ADD_FILTERS(new ArrayList<>(){{add(UserProfile.BotState.ADD_FILTERS.name());}}),
    ADD_FILTER_WORKPLACE(new ArrayList<>(){{add(UserProfile.BotState.ADD_FILTER_WORKPLACE.name());}}),
    WATCH_LIST_OF_JOBS(new ArrayList<>(){{add(UserProfile.BotState.NEW.name());
        add(UserProfile.BotState.APPLIED.name());
        add(UserProfile.BotState.SEARCHES.name());}}),
    MAKE_FIRST_SEARCH(new ArrayList<>(){{add("/make_first_search");}}),
    GET_ALL_SEARCHES(new ArrayList<>(){{add("/get_all_searches");}}),
    GET_APPLIED_JOBS(new ArrayList<>(){{add("/get_applied_jobs");}}),
    GET_NEW_JOBS(new ArrayList<>(){{add("/get_new_jobs");}});

    private final List<String> values;


    CommandName(final List<String> values) {
        this.values = values;
    }

    static CommandName getValue(final String val) {
        for (CommandName e: CommandName.values()) {
            if (e.values.contains(val)) {
                return e;
            }
        }
        return null;
    }
}

package com.gbyzzz.linkedinjobsbot.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface MessageService {
    SendMessage getNewJobByUserId(Long userId, String command);
    SendMessage getAppliedJobByUserId(Long userId, Long currentAppliedJobId, Long searchParamsId, int page, String direction);
    SendMessage getSearchByUserId(Long userId, Long currentSearchParamsId, int page, String direction);
}

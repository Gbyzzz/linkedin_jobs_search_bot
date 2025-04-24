package com.gbyzzz.linkedinjobsbot.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface MessageService {
    SendMessage getNewJobByUserId(Long userId, String[] commands);
    SendMessage getAppliedJobByUserId(Long userId, String[] commands);
    SendMessage getSearchByUserId(Long userId, String[] commands);
}
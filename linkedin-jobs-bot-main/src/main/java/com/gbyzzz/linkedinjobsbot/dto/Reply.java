package com.gbyzzz.linkedinjobsbot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reply {
    private SendMessage sendMessage;
    private boolean isUpdate;
}
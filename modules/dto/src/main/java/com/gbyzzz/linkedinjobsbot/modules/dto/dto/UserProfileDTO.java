package com.gbyzzz.linkedinjobsbot.modules.dto.dto;

import java.util.Date;

public record UserProfileDTO(
        Long chatId,
        String username,
        String botState,
        Date registeredAt,
        String userRole,
        String userPic
) {
}

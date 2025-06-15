package com.gbyzzz.linkedinjobsbotapi.controller.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TelegramLoginRequest {
    Long auth_date;
    String first_name;
    String hash;
    Long id;
    String photo_url;
    String username;
}

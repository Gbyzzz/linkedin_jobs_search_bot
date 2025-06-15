package com.gbyzzz.linkedinjobsbotapi.service;

import com.gbyzzz.linkedinjobsbotapi.controller.payload.request.TelegramLoginRequest;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<?> signIn(String username, String password) throws Exception;

//    boolean changePassword(ChangePasswordRequest changePasswordRequest) throws IOException;

    boolean sendOnetimePassword(String email);

    ResponseEntity<?> signInUsingTelegram(TelegramLoginRequest loginRequest) throws Exception;
}

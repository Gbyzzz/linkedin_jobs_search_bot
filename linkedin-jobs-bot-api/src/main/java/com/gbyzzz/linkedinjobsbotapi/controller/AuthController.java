package com.gbyzzz.linkedinjobsbotapi.controller;

import com.gbyzzz.linkedinjobsbotapi.controller.payload.request.LoginRequest;
import com.gbyzzz.linkedinjobsbotapi.controller.payload.request.TelegramLoginRequest;
import com.gbyzzz.linkedinjobsbotapi.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



@RestController
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class AuthController {

    private AuthService authService;

    @PostMapping("/sign_in")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest)
            throws Exception {
        return authService.signIn(loginRequest.getUsername(), loginRequest.getPassword());
    }

        @PostMapping("/telegram_sign_in")
    public ResponseEntity<?> authenticateUserWithTelegram(@RequestBody TelegramLoginRequest loginRequest)
            throws Exception {
        return authService.signInUsingTelegram(loginRequest);
    }

}

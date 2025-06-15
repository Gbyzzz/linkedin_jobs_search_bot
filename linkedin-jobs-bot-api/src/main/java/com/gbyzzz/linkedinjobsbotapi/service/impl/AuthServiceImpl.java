package com.gbyzzz.linkedinjobsbotapi.service.impl;

import com.gbyzzz.linkedinjobsbot.modules.postgresdb.dto.mapper.UserProfileDTOMapper;
import com.gbyzzz.linkedinjobsbot.modules.postgresdb.entity.UserProfile;
import com.gbyzzz.linkedinjobsbot.modules.postgresdb.repository.UserProfileRepository;
import com.gbyzzz.linkedinjobsbotapi.controller.payload.request.TelegramLoginRequest;
import com.gbyzzz.linkedinjobsbotapi.controller.payload.response.JwtResponse;
import com.gbyzzz.linkedinjobsbotapi.security.jwt.JwtUtils;
import com.gbyzzz.linkedinjobsbotapi.security.services.UserDetailsImpl;
import com.gbyzzz.linkedinjobsbotapi.security.telegram.TelegramAuthService;
import com.gbyzzz.linkedinjobsbotapi.security.telegram.UsernameOnlyAuthenticationProvider;
import com.gbyzzz.linkedinjobsbotapi.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

//    private final PasswordEncoder encoder;
//    private final UserProfileService userService;
//    private final KafkaService kafkaService;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final UserProfileRepository userRepository;
    private final UserProfileDTOMapper mapper;
    private final TelegramAuthService telegramAuthService;
    private final UsernameOnlyAuthenticationProvider usernameOnlyAuthenticationProvider;

//    @Value("${gbyzzz.url.to.validate}")
//    String urlToValidate;

    @Override
    public ResponseEntity<?> signIn(String username, String password) throws Exception {
        return authenticate(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,
                        password)));
    }

//    @Override
//    public boolean changePassword(ChangePasswordRequest changePasswordRequest) throws IOException {
//        UserProfile user;
//        boolean valid = false;
//        if(changePasswordRequest.getEmail().equals("")) {
//
//            ResponseEntity<String> response = new RestTemplate().postForEntity(
//                        urlToValidate + "/pass", changePasswordRequest.getOldPassword(), String.class);
//            valid = response.hasBody();
//
//            if (valid) {
//                user = userService.findByEmail(response.getBody());
//                user.setPassword(encoder.encode(changePasswordRequest.getNewPassword()));
//                userService.updateUser(user, null);
//            }
//        } else {
//            if (!isPasswordValid(changePasswordRequest)) {
//                return false;
//            }
//            user = userService.findByEmail(changePasswordRequest.getEmail());
//            user.setPassword(encoder.encode(changePasswordRequest.getNewPassword()));
//            valid = userService.updateUser(user, null) != null;
//        }
//        return valid;
//    }

    @Override
    public boolean sendOnetimePassword(String email) {
        return false;
    }

    @Override
    public ResponseEntity<?> signInUsingTelegram(TelegramLoginRequest loginRequest) throws Exception {
        if(telegramAuthService.isDataValid(loginRequest)) {
            Optional<UserProfile> userProfile = userRepository.findById(loginRequest.getId());
            userProfile.ifPresent(profile -> checkImgUrl(loginRequest.getPhoto_url(), profile));
            return authenticate(usernameOnlyAuthenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                    null)));
        }
        return ResponseEntity.status(403).build();
    }

    private void checkImgUrl(String photoUrl, UserProfile userProfile) {
        if(!Objects.equals(userProfile.getUserPic(), photoUrl)) {
            userProfile.setUserPic(photoUrl);
        }
    }

    private ResponseEntity<?> authenticate(Authentication authentication) throws Exception {
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        UserProfile user = userRepository.findById(userDetails.getId()).orElseThrow(
                ()-> new Exception("User with id " + userDetails.getId() + "haven't bee found")
        );
        return ResponseEntity.ok(new JwtResponse(jwt, mapper.toDTO(user)));
    }

}

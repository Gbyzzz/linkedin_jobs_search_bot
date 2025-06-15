package com.gbyzzz.linkedinjobsbotapi.controller;

import com.gbyzzz.linkedinjobsbot.modules.postgresdb.entity.UserProfile;
import com.gbyzzz.linkedinjobsbot.modules.postgresdb.service.UserProfileService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class UserProfileController {

    private final UserProfileService userProfileService;

    @GetMapping("/all")
    @PreAuthorize(value = "hasAnyAuthority('ADMIN')")
    public List<UserProfile> getUserProfile() {
       return userProfileService.getAll();
    }
}

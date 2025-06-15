package com.gbyzzz.linkedinjobsbotapi.security.telegram;

import com.gbyzzz.linkedinjobsbotapi.security.services.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UsernameOnlyAuthenticationProvider implements AuthenticationProvider {
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();

        // Load user details by username
        UserDetails user = userDetailsService.loadUserByUsername(username);

        // You can add extra verification logic here if needed
        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
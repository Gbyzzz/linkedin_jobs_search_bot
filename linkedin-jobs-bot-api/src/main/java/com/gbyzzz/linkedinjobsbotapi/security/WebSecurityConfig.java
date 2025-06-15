package com.gbyzzz.linkedinjobsbotapi.security;

import com.gbyzzz.linkedinjobsbotapi.security.jwt.AuthTokenFilter;
import com.gbyzzz.linkedinjobsbotapi.security.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, jsr250Enabled = true, securedEnabled = true)
public class WebSecurityConfig implements WebMvcConfigurer {


    private final AuthenticationProvider authenticationProvider;
    private final AuthenticationService authenticationService;
    private final AuthTokenFilter authTokenFilter;

    public WebSecurityConfig(AuthenticationProvider authenticationProvider,
                             AuthenticationService authenticationService,
                             AuthTokenFilter authTokenFilter) {
        this.authenticationProvider = authenticationProvider;
        this.authenticationService = authenticationService;
        this.authTokenFilter = authTokenFilter;
    }

    @Value("${app.allowed-origins}")
    private String allowedOriginsRaw;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorizeHttpRequests) ->
                        authorizeHttpRequests.requestMatchers("/**")
                                .permitAll())
                .sessionManagement((sessionManagement) ->
                        sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .logout((logout) ->
                        logout.logoutUrl("/logout")
                                .addLogoutHandler(authenticationService)
                                .logoutSuccessHandler((request, response,
                                                       authentication) ->
                                        SecurityContextHolder.clearContext()));
        return http.build();
    }

    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(allowedOriginsRaw.split(",")) // or your frontend domain
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("Authorization", "Content-Type");
    }


}

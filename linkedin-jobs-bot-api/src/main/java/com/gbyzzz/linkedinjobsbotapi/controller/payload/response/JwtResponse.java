package com.gbyzzz.linkedinjobsbotapi.controller.payload.response;

import com.gbyzzz.linkedinjobsbot.modules.dto.dto.UserProfileDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponse {
	private String token;
	private String type = "Bearer";
	private UserProfileDTO user;

	public JwtResponse(String token, UserProfileDTO user) {
		this.token = token;
		this.user = user;
	}
}

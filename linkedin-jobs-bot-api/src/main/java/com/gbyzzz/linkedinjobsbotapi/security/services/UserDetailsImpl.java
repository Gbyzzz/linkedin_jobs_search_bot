package com.gbyzzz.linkedinjobsbotapi.security.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gbyzzz.linkedinjobsbot.modules.postgresdb.entity.UserProfile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class UserDetailsImpl implements UserDetails {
	@Serial
	private static final long serialVersionUID = 1L;

	private final Long id;

	private final String username;

	@JsonIgnore
	private final String password;

	private final Collection<? extends GrantedAuthority> authorities;

	public UserDetailsImpl(Long id, String username, String password,
			Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.authorities = authorities;
	}

	public static UserDetailsImpl build(UserProfile user) {
		List<GrantedAuthority> authorities = Collections.
				singletonList(new SimpleGrantedAuthority(user.getUserRole().toString()));


		return new UserDetailsImpl(
				user.getChatId(),
				user.getUsername(), 
				user.getPassword(),
				authorities);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public Long getId() {
		return id;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserDetailsImpl user = (UserDetailsImpl) o;
		return Objects.equals(id, user.id);
	}
}

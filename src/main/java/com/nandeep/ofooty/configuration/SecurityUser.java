package com.nandeep.ofooty.configuration;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import com.nandeep.ofooty.modal.User;
import com.nandeep.ofooty.util.Role;

public class SecurityUser extends User implements UserDetails {

	// must for UserDetails interface
	private static final long serialVersionUID = 1L;
	
	public SecurityUser(User user) {
		super(user);
	}
	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Role roles = super.getUserRole();
		System.out.println("Security user roles: " + roles);
		return AuthorityUtils.commaSeparatedStringToAuthorityList(roles.toString());
	}

	@Override
	public String getUsername() {
		return super.getUserName();
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

}

package com.dxc.restcontroller.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dxc.restcontroller.entity.UserEntity;
import com.dxc.restcontroller.entity.UserRole;
import com.dxc.restcontroller.repository.UserRepository;

@Service("userServiceImpl")
public class UserServiceImpl implements UserDetailsService  {

	@Autowired
	@Qualifier("userRepository")
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity user = userRepository.findByUsername(username);		
		List<GrantedAuthority> authorities = buildAuthorities(user.getUserRoles());
		return buildUser(user, authorities);
	}

	private User buildUser(UserEntity user, List<GrantedAuthority> authorities) {
		return new User(user.getUsername(), user.getPassword(), user.isEnabled(),
				true, true, true, authorities);
		
	}
	
	public List<GrantedAuthority> buildAuthorities(Set<UserRole> userRoles) {
		Set<GrantedAuthority> auths = new HashSet<GrantedAuthority>();
		
		for(UserRole  userRole : userRoles){
			auths.add(new SimpleGrantedAuthority(userRole.getRole()));
			
		}
		return new ArrayList<GrantedAuthority>(auths);
	}
	
}

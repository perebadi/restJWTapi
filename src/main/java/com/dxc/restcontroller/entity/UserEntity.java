package com.dxc.restcontroller.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="user")
public class UserEntity {
	
	@Id
	@GeneratedValue
	@Column(name="id", nullable=false, unique=true)
	private int id;
	
	@Column(name="username", nullable=false, unique=true)
	private String username;
	
	@Column(name="name", nullable=false)
	private String name;

	@Column(name="password", nullable=false)
	private String password;
	
	@Column(name="enabled", nullable=false)
	private boolean enabled;
	
	@OneToMany(fetch=FetchType.EAGER, mappedBy="user")
	private Set<UserRole> userRoles;

	public Set<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public UserEntity() {}
	
}

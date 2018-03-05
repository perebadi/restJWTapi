package com.dxc.restcontroller.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="user_role", uniqueConstraints = @UniqueConstraint(
		columnNames= {"role", "user_id"}))
public class UserRole {

	@Id
	@GeneratedValue
	@Column(name="id", nullable=false, unique=true)
	private int id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private UserEntity user;
	
	@Column(name="role", nullable=false)
	private String role;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	public UserRole() {}
	
}

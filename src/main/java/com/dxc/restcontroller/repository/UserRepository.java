package com.dxc.restcontroller.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dxc.restcontroller.entity.UserEntity;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<UserEntity, Serializable> {

	public abstract UserEntity findByUsername(String username);
	
}

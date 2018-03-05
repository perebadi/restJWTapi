package com.dxc.restcontroller.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dxc.restcontroller.entity.UserRole;

@Repository("roleRepository")
public interface RoleRepository extends JpaRepository<UserRole, Serializable> {

}

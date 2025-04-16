package com.uas.locobooking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uas.locobooking.models.Roles;

public interface RolesRepository extends JpaRepository<Roles, String> {
    Roles findByRoleName(String roleName);
}


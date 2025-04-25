package com.uas.locobooking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uas.locobooking.models.JustLogged;

public interface JustLoggedRepository extends JpaRepository<JustLogged,Integer>{
    JustLogged findFirstByOrderByIdAsc();
}

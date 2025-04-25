package com.uas.locobooking.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.uas.locobooking.models.Users;

public interface UsersRepository extends JpaRepository<Users, String> {

        Optional<Users> findByUsername(String email);
        
        @Query("delete from Users where username=:email")
        void deleteByEmail(String email);
}


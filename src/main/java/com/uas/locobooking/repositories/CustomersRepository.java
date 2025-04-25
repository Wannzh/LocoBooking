package com.uas.locobooking.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.uas.locobooking.models.Customer;

public interface CustomersRepository extends JpaRepository<Customer, String> {

    @Query("delete from Customer where email=:email")
    void deleteByEmail(String email);

    Optional<Customer> findByEmail(String email);

    


}


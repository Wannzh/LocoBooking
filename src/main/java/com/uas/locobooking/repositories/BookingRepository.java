package com.uas.locobooking.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.uas.locobooking.models.Booking;
import com.uas.locobooking.models.Customer;

public interface BookingRepository extends JpaRepository<Booking,String> {
 
        @Query("SELECT b FROM Booking b WHERE b.customer.email = :email")
        List<Booking> findBookingsByCustomerEmail(@Param("email") String email);

        Optional<Booking> findByCustomer(Customer customer);

}

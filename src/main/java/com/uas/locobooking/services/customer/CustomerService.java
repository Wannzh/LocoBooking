package com.uas.locobooking.services.customer;

import org.springframework.stereotype.Service;

import com.uas.locobooking.dto.customer.CustomerRequestDto;
import com.uas.locobooking.models.Customer;
import com.uas.locobooking.models.Users;

@Service
public interface CustomerService {
    Customer register(CustomerRequestDto dto);

    void deleteUser(String email);

    Users getByEmail(String email);

    // Customer getCustomerByEmail(String email);

    
}



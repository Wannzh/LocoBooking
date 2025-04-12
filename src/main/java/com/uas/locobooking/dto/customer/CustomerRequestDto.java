package com.uas.locobooking.dto.customer;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Service
public class CustomerRequestDto {

    private String name;

    private String address;

    private String email;

    private String phoneNumber;

    private String password;
}
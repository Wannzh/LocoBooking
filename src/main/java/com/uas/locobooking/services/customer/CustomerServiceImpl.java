package com.uas.locobooking.services.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.uas.locobooking.constants.RoleConstant;
import com.uas.locobooking.dto.customer.CustomerRequestDto;
import com.uas.locobooking.models.Customer;
import com.uas.locobooking.models.Users;
import com.uas.locobooking.repositories.CustomersRepository;
import com.uas.locobooking.repositories.RolesRepository;
import com.uas.locobooking.repositories.UsersRepository;
import com.uas.locobooking.services.EmailService;

import jakarta.transaction.Transactional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomersRepository customerRepository;
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    RolesRepository rolesRepository;
    @Autowired
    EmailService emailService;

    @Override
    @Transactional
    public Customer register(CustomerRequestDto dto) {
        try {

            List<Customer> customers = customerRepository.findAll().stream()
                    .filter(data -> data.getEmail().equalsIgnoreCase(dto.getEmail())).map(n -> n).toList();

            if (!customers.isEmpty())
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email was registered");
                
                Users user = new Users();
                user.setUsername(dto.getEmail());
                user.setPassword(passwordEncoder.encode(dto.getPassword()));
                user.setRoles(rolesRepository.findByRoleName(RoleConstant.USER_ROLE));
                sendEmail(dto.getEmail());
                usersRepository.save(user);
                System.out.println("cek 123");

            Customer customer = new Customer();
            customer.setName(dto.getName());
            customer.setEmail(dto.getEmail());
            customer.setAddress(dto.getAddress());
            customer.setPhoneNumber(dto.getPhoneNumber());
            customer.setUsers(user);
            

            return customerRepository.save(customer);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    private void sendEmail(String to) {
        String subject = "Customer Registration";
        String text = "Selamat anda berhasil mendaftar";

        emailService.sendMailMessage(to, subject, text);
    }

    @Override
    public void deleteUser(String email) {
        customerRepository.deleteByEmail(email);
    }

    @Override
    public Users getByEmail(String email) {

        try {

            return usersRepository.findByUsername(email).orElse(null);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}

package com.uas.locobooking.controllers.auth.Customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.uas.locobooking.constants.MessageConstant;
import com.uas.locobooking.dto.GeneralResponse;
import com.uas.locobooking.dto.customer.CustomerRequestDto;
import com.uas.locobooking.models.Customer;
import com.uas.locobooking.services.customer.CustomerService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Service
@CrossOrigin("*")
@Slf4j
@RestController
@RequestMapping("/api/customer")
@Tag(name = "Customer")
public class RegisterController {

    @Autowired
    private CustomerService customerService;
    
    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody CustomerRequestDto dto) {
        try {
            Customer response = customerService.register(dto);
            return ResponseEntity.ok()
                    .body(GeneralResponse.success(response,
                            MessageConstant.OK_POST_DATA));
        } catch (ResponseStatusException e) {
            log.info(e.getReason());
            return ResponseEntity.status(e.getStatusCode())
                    .body(GeneralResponse.error(MessageConstant.BAD_REQUEST));
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(GeneralResponse.error(MessageConstant.INTERNAL_SERVER_ERROR));
        }
    }




}

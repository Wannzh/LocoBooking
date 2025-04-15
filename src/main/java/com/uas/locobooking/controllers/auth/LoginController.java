package com.uas.locobooking.controllers.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.javaproject.locobooking.constants.MessageConstant;
import com.uas.locobooking.dto.GeneralResponse;
import com.uas.locobooking.dto.auth.LoginRequestDto;
import com.uas.locobooking.dto.auth.LoginResponseDto;
import com.uas.locobooking.dto.auth.ResetPasswordRequestDto;
import com.uas.locobooking.services.auth.LoginService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;



@CrossOrigin("*")
@RestController
@RequestMapping("/api/auth")
@Slf4j
@Tag(name = "Login")
public class LoginController {

    @Autowired
    LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequestDto loginRequestDto) {
        try {

            LoginResponseDto response = loginService.login(loginRequestDto);
            return ResponseEntity.ok()
                    .body(GeneralResponse.success(response,
                            MessageConstant.OK_POST_DATA));
        } catch (ResponseStatusException e) {

            log.info(e.getMessage());
            return ResponseEntity.status(e.getStatusCode())
                    .body(GeneralResponse.error(e.getReason()));
        } catch (Exception e) {

            log.info(e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(GeneralResponse.error(MessageConstant.INTERNAL_SERVER_ERROR));
        }
    }

    @PostMapping("/forgot-password")
    @Operation(summary = "Request For Reset Password", description = "A code will send to email and use the code for confirm reset your password")
    public ResponseEntity<Object> forgotPassword(@RequestParam String email) {
        try {

            loginService.sendForgotPassword(email);
            return ResponseEntity.ok()
                    .body(GeneralResponse.success(null,
                            MessageConstant.OK_POST_DATA));
        } catch (ResponseStatusException e) {

            log.info(e.getMessage());
            return ResponseEntity.status(e.getStatusCode())
                    .body(GeneralResponse.error(e.getReason()));
        } catch (Exception e) {

            log.info(e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(GeneralResponse.error(MessageConstant.INTERNAL_SERVER_ERROR));
        }
    }

    @PostMapping("/reset-password")
    @Operation(summary = "Set A New Password For Your Account", description = "Set a new password for your account and confirm the code was send by email")
    public ResponseEntity<Object> resetPassword(@RequestBody ResetPasswordRequestDto dto) {

        try {

            loginService.resetPassword(dto);

            return ResponseEntity.ok()
                    .body(GeneralResponse.success(null, MessageConstant.OK_POST_DATA));
        } catch (ResponseStatusException e) {

            log.info(e.getMessage());
            return ResponseEntity.status(e.getStatusCode())
                    .body(GeneralResponse.error(e.getReason()));
        } catch (Exception e) {

            log.info(e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(GeneralResponse.error(MessageConstant.INTERNAL_SERVER_ERROR));
        }
    }

 

}
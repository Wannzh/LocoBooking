package com.uas.locobooking.services.auth;

import java.util.Date;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.uas.locobooking.dto.auth.LoginRequestDto;
import com.uas.locobooking.dto.auth.LoginResponseDto;
import com.uas.locobooking.dto.auth.ResetPasswordRequestDto;
import com.uas.locobooking.models.Users;
import com.uas.locobooking.repositories.CustomersRepository;
import com.uas.locobooking.repositories.UsersRepository;
import com.uas.locobooking.services.EmailService;
import com.uas.locobooking.util.JwtUtil;

@Service
public class LoginServiceImpl implements LoginService {
    

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    CustomersRepository customerRepository;
    
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtil jwtUtil;


    @Autowired
    EmailService emailService;

    @Override
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {

        try {

            Users user = usersRepository
                    .findByUsername(loginRequestDto.getUsername())
                    .orElse(null);

            if (user == null)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Invalid username or password");

            boolean isMatch = passwordEncoder.matches(loginRequestDto.getPassword(),
                    user.getPassword());

            if (!isMatch)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid username or password");

            LoginResponseDto loginResponseDto = new LoginResponseDto();
            loginResponseDto.setUsername(user.getUsername());
            loginResponseDto.setRole(user.getRoles().getRoleName());
            loginResponseDto.setToken(jwtUtil.generateToken(user));
            return loginResponseDto;
        } catch (Exception e) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public void sendForgotPassword(String email) {

        Users users = usersRepository.findByUsername(email).orElse(null);
        if (users == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid email");

        String OTP = RandomStringUtils.randomAlphanumeric(5);
        String encodedOTP = passwordEncoder.encode(OTP);

        users.setOtp(encodedOTP);
        users.setOtpRequestedTime(new Date());

        usersRepository.save(users);

        emailService.sendResetPassword(email, OTP);
    }

    @Override
    public void resetPassword(ResetPasswordRequestDto dto) {

        try {

            Users users = usersRepository.findByUsername(dto.getEmail()).orElse(null);
            if (users == null)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User Not Found");
            if (!users.isOtpRequired())
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "OTP was expired");

            if (!passwordEncoder.matches(dto.getOneTimePassword(), users.getOtp()))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "OTP was incorrect");
            users.setOtp(null);
            users.setOtpRequestedTime(null);
            users.setPassword(passwordEncoder.encode(dto.getNewPassword()));
            usersRepository.save(users);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }


    @Override
    public void requestDeleteAccount(String email) { 
        emailService.sendRequestDeleteAccount();
    }
}


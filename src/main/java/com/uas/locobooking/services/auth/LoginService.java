package com.uas.locobooking.services.auth;

import com.uas.locobooking.dto.auth.LoginRequestDto;
import com.uas.locobooking.dto.auth.LoginResponseDto;
import com.uas.locobooking.dto.auth.ResetPasswordRequestDto;

public interface LoginService {

    LoginResponseDto login(LoginRequestDto loginRequestDto);

    void sendForgotPassword(String email);

    void resetPassword(ResetPasswordRequestDto dto);

    void requestDeleteAccount(String email);
}

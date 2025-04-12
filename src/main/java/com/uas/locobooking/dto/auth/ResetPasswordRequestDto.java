package com.uas.locobooking.dto.auth;

import lombok.Data;

@Data
public class ResetPasswordRequestDto {

    String email;
    String oneTimePassword;
    String newPassword;

}

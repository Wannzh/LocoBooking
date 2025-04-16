
package com.uas.locobooking.models;
import java.util.Date;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false)
    private Roles roles;

    // One Time Password
    public static final long OTP_VALID_DURATION = 5 * 60 * 1000; // 5 Minutes

    @Column(name = "otp")
    private String otp;

    @Column(name = "otp_requested_time")
    private Date otpRequestedTime;

    public boolean isOtpRequired() {
        if (this.getOtp() == null)
            return false;
        long currentTimeInMilis = System.currentTimeMillis();
        long otpRequestedTimeInMilis = this.otpRequestedTime.getTime();

        if (otpRequestedTimeInMilis + OTP_VALID_DURATION < currentTimeInMilis)
            return false;

        return true;
    }
}

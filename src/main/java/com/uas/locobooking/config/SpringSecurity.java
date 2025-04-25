package com.uas.locobooking.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.uas.locobooking.constants.RoleConstant;
import com.uas.locobooking.security.JwtFilter;
import com.uas.locobooking.exception.CustomAccessDeniedException;
import com.uas.locobooking.exception.CustomUnAuthorizeException;

@Configuration
@EnableWebSecurity
public class SpringSecurity {

        @Bean
        PasswordEncoder getPasswordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Autowired
        JwtFilter jwtFilter;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .csrf(csrf -> csrf.disable())
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .exceptionHandling(ex -> ex
                                                .authenticationEntryPoint(new CustomUnAuthorizeException())
                                                .accessDeniedHandler(new CustomAccessDeniedException()))
                                .authorizeHttpRequests(auth -> auth
                                                // Public endpoints
                                                .requestMatchers(
                                                                "/api/customer/register",
                                                                "/api/auth/reset-password",
                                                                "/api/auth/forgot-password",
                                                                "/api/auth/login",
                                                                "/swagger-ui/**",
                                                                "/v3/api-docs/**")
                                                .permitAll()

                                                // ADMIN-only access
                                                .requestMatchers(
                                                                "/api/schedules",
                                                                "/api/trains",
                                                                "/api/routes",
                                                                "/api/carriages",
                                                                "/api/stations",
                                                                "/api/trains/**",
                                                                "/api/schedules/**",
                                                                "/api/routes/**",
                                                                "/api/carriages/**",
                                                                "/api/stations/**"
                                                // "/api/report/report-daily",
                                                // "/api/report/report-monthly",
                                                // "/api/report/report-annual",
                                                // "/api/report/report-pdf",
                                                // "/api/auth/delete-user"
                                                ).hasAuthority(RoleConstant.ADMIN_ROLE)

                                                // USER-only access
                                                .requestMatchers(
                                                                "/api/booking/save-booking/**",
                                                                "/api/booking/get-by-email",
                                                                "/api/booking/payment",
                                                                "/api/booking/payment-success",
                                                                "/api/booking/confirmation/**",
                                                                "/api/booking",
                                                                "/api/booking/check-pesanan",
                                                                "/api/booking/**",
                                                                "/api/booking/schedule"
                                                                )
                                                .hasAuthority(RoleConstant.USER_ROLE)

                                                // Admin or User
                                                .requestMatchers(
                                                                "/api/customer/get-user",
                                                                "/api/booking/cancel-booking/**",
                                                                "/api/booking/all-booking")
                                                .hasAnyAuthority(RoleConstant.ADMIN_ROLE, RoleConstant.USER_ROLE)

                                                // All others require auth
                                                .anyRequest().authenticated())
                                .addFilterAfter(jwtFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }
}
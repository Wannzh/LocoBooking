package com.uas.locobooking.security;

import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import com.uas.locobooking.dto.auth.UserCredentialsDto;
import com.uas.locobooking.util.JwtUtil;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        try {
            String accessToken = jwtUtil.resolveToken(request);
            if (accessToken == null) {
                filterChain.doFilter(request, response);
                return;

            }

            Claims claims = jwtUtil.resolveClaims(request);
            if (claims != null && jwtUtil.validateClaims(claims)) {
                String username = claims.getSubject();
                String role = claims.get("role").toString();

                UserCredentialsDto credentialPayload = new UserCredentialsDto();
                // credentialPayload.setUserId(Integer.valueOf(claims.getId()));
                credentialPayload.setUsername(claims.get("username").toString());

                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        username,
                        credentialPayload,
                        Collections.singleton(new SimpleGrantedAuthority(role)));

                        // System.out.println(authentication);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        } catch (Exception e) {
            // log.info(e.getLocalizedMessage());
            System.out.println(e.getLocalizedMessage());
        }

        filterChain.doFilter(request, response);
    }
}
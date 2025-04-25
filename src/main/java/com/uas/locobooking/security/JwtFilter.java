package com.uas.locobooking.security;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
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

// @Component
// public class JwtFilter extends OncePerRequestFilter {

//     @Autowired
//     JwtUtil jwtUtil;

//     @Override
//     protected void doFilterInternal(HttpServletRequest request,
//                                     HttpServletResponse response,
//                                     FilterChain filterChain)
//             throws ServletException, IOException {

//         try {
//             String accessToken = jwtUtil.resolveToken(request);
//             if (accessToken == null) {
//                 filterChain.doFilter(request, response);
//                 return;
//             }

//             Claims claims = jwtUtil.resolveClaims(request);
//             if (claims != null && jwtUtil.validateClaims(claims)) {
//                 String username = claims.getSubject();
//                 String role = claims.get("role").toString();

//                 // ✅ Tambahkan prefix ROLE_
//                 SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);

//                 Authentication authentication = new UsernamePasswordAuthenticationToken(
//                         username,
//                         null,
//                         Collections.singleton(authority)
//                 );
//                 SecurityContextHolder.getContext().setAuthentication(authentication);

//                 // 🪵 Optional logging untuk debug
//                 System.out.println("Authenticated user: " + username);
//                 System.out.println("Granted Authority: " + authority.getAuthority());
//             }
//         } catch (Exception e) {
//             response.setStatus(HttpStatus.FORBIDDEN.value());
//             response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//             response.getWriter().write("Your access is forbidden");
//             return;
//         }

//         filterChain.doFilter(request, response);
//     }
// }

@Service
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    JwtUtil jwtUtil;

    @SuppressWarnings("unchecked")
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
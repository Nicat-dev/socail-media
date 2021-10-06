package com.nicat.socailmedia.security.service;

import com.nicat.socailmedia.exception.AuthenticationException;
import com.nicat.socailmedia.exception.TokenNotFoundException;
import com.nicat.socailmedia.security.dto.AuthRequest;
import com.nicat.socailmedia.security.dto.AuthResponse;
import com.nicat.socailmedia.security.model.JwtUser;
import com.nicat.socailmedia.security.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;

    public AuthResponse createAuthenticationToken(AuthRequest request){
        authenticate(request.getEmail(), request.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = jwtTokenUtil.generateToken(userDetails);
        return new AuthResponse(token);
    }



    public JwtUser getUserByToken(String authToken){
        if(Objects.isNull(authToken) || authToken.length() < 7)
            throw new TokenNotFoundException("cant get user by token");

        String token = authToken.substring(7);

        String email = jwtTokenUtil.getUsernameFromToken(token);
        return (JwtUser) userDetailsService.loadUserByUsername(email);
    }

    private void authenticate(String email, String password) {
        Objects.requireNonNull(email);
        Objects.requireNonNull(password);
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        }catch (DisabledException e){
            throw new AuthenticationException("user is disabled");
        }catch (BadCredentialsException e){
            throw new AuthenticationException("bad credentials");
        }
    }
}
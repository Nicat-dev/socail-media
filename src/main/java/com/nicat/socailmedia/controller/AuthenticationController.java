package com.nicat.socailmedia.controller;

import com.nicat.socailmedia.dto.request.CreateAccountRequest;
import com.nicat.socailmedia.dto.response.ApiMessage;
import com.nicat.socailmedia.exception.DomainExistException;
import com.nicat.socailmedia.security.dto.AuthRequest;
import com.nicat.socailmedia.security.dto.AuthResponse;
import com.nicat.socailmedia.security.service.AuthenticationService;
import com.nicat.socailmedia.service.AccountService;
import com.nicat.socailmedia.util.ApiBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController implements ApiBuilder {

    private final AuthenticationService authenticationService;
    private final AccountService accountService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/signing")
    public ResponseEntity<AuthResponse> authenticateUser(@Valid @RequestBody AuthRequest loginRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(authenticationService.createAuthenticationToken(loginRequest));
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiMessage> registerUser(@Valid @RequestBody CreateAccountRequest signUpRequest) {
        if (accountService.doesExistByUsername(signUpRequest.getEmail()))
            throw new DomainExistException("Exist this domain by following username := " + signUpRequest.getEmail());
        accountService.create(signUpRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(generateOkay());
    }
}
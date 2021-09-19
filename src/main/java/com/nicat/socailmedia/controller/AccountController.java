package com.nicat.socailmedia.controller;

import com.nicat.socailmedia.domain.Account;
import com.nicat.socailmedia.dto.request.CreateAccountRequest;
import com.nicat.socailmedia.dto.response.AccountResponse;
import com.nicat.socailmedia.dto.response.ApiMessage;
import com.nicat.socailmedia.mapper.AccountMapper;
import com.nicat.socailmedia.service.AccountService;
import com.nicat.socailmedia.util.ApiBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account")
public class AccountController implements ApiBuilder {

    private final AccountService accountService;
    private final AccountMapper accountMapper;

    @PostMapping
    public ResponseEntity<ApiMessage> create(
            @Valid @RequestBody CreateAccountRequest request){
        log.info("Service for creation account accepted");
        accountService.create(request);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(generateOkay());
    }

    @GetMapping
    public ResponseEntity<List<AccountResponse>> findAll(){
        List<AccountResponse> accountResponses =accountService.findAll().stream()
                .map(accountMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(accountResponses);
    }


}

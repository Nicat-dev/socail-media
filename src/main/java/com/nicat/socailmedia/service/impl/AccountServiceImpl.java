package com.nicat.socailmedia.service.impl;

import com.nicat.socailmedia.domain.Account;
import com.nicat.socailmedia.dto.request.CreateAccountRequest;
import com.nicat.socailmedia.exception.DomainNotFoundException;
import com.nicat.socailmedia.exception.MethodNullArgumentException;
import com.nicat.socailmedia.mapper.AccountMapper;
import com.nicat.socailmedia.repository.AccountRepository;
import com.nicat.socailmedia.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service("AccountService")
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Override
    public void create(CreateAccountRequest request) {
        log.info("Service for creation account accepted");
        accountRepository.save(accountMapper.toEntity(request));
    }

    @Override
    public Account findById(Long id) {
        if (id == null)
            throw new MethodNullArgumentException("Id can not be null on findById");
        return accountRepository.findById(id).orElseThrow(
                ()-> new DomainNotFoundException("Account not found by id : "+id));
    }

    @Override
    public List<Account> findAll() {
        List<Account> accounts = accountRepository.findAll();
        if (accounts.isEmpty())
            throw new DomainNotFoundException("no account avaible");
        return accounts;
    }
}

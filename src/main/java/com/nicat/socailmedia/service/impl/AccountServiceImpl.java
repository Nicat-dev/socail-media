package com.nicat.socailmedia.service.impl;

import com.nicat.socailmedia.domain.Account;
import com.nicat.socailmedia.dto.request.CreateAccountRequest;
import com.nicat.socailmedia.dto.request.UpdateAccountRequset;
import com.nicat.socailmedia.enums.EnumAvaibleStatus;
import com.nicat.socailmedia.exception.DomainNotFoundException;
import com.nicat.socailmedia.exception.MethodNullArgumentException;
import com.nicat.socailmedia.mapper.AccountMapper;
import com.nicat.socailmedia.repository.AccountRepository;
import com.nicat.socailmedia.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service("AccountService")
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Override
    @Transactional
    public void create(CreateAccountRequest request) {
        log.info("Service for creation account accepted");
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        accountRepository.save(accountMapper.toEntity(request));
    }

    @Override
    public Account findById(Long id) {
        if (id == null)
            throw new MethodNullArgumentException("Id can not be null on findById");
        return accountRepository.findById(id).orElseThrow(
                () -> new DomainNotFoundException("Account not found by id : " + id));
    }

    @Override
    public List<Account> findAll() {
        List<Account> accounts = accountRepository.findAllByStatus(EnumAvaibleStatus.ACTIVE.getValue());
        if (accounts.isEmpty())
            throw new DomainNotFoundException("no account avaible");
        return accounts;
    }

    @Override
    public void deleteById(Long id){
        if (id == null)
            throw new MethodNullArgumentException("Id can not be null on findById");
        if (!accountRepository.existsById(id))
            throw new DomainNotFoundException("Account not found by id : " + id);
        Account account = accountRepository.findAccountByAccountIdAndStatus(id, EnumAvaibleStatus.ACTIVE.getValue());
        if (account==null)
            throw new DomainNotFoundException("account not found");
        account.setStatus(EnumAvaibleStatus.DEACTIVE.getValue());
        accountRepository.save(account);

    }

    @Override
    public boolean doesExistByUsername(String email) {
        if (email == null || email.isEmpty())
            throw new MethodNullArgumentException("Id can not be null on doesExistByUsername");
        return accountRepository.findAccountByEmail(email).isPresent();
    }

    @Override
    @Transactional
    public void update(UpdateAccountRequset requset, Long id) {
        if (id == null) throw new IllegalArgumentException("Method Arg is null");
        Account account = accountRepository.findById(id).orElseThrow(
                () -> new DomainNotFoundException("User not found by id := " + id));
        accountRepository.save(update(account, requset));
    }


    private Account update(Account account, UpdateAccountRequset requset) {
        Account account1 = Account.builder()
                .accountId(account.getAccountId())
                .fullName(requset.getFullName() == null ?
                        account.getFullName() : account.getFullName())
                .password(account.getPassword() == null ?
                        account.getPassword() : account.getPassword())
                .email(account.getEmail() == null ?
                        account.getEmail() : account.getEmail())
                .build();
        return account1;
    }
}

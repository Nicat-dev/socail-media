package com.nicat.socailmedia.service;

import com.nicat.socailmedia.domain.Account;
import com.nicat.socailmedia.dto.request.CreateAccountRequest;
import com.nicat.socailmedia.dto.request.UpdateAccountRequset;

import java.util.List;

public interface AccountService {
    void create(CreateAccountRequest createAccountRequest);
    Account findById(Long id);
    List<Account> findAll();
    void deleteById(Long id);
    boolean doesExistByUsername(String email);
    void update(UpdateAccountRequset requset, Long id);
}

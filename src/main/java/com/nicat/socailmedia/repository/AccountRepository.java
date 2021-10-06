package com.nicat.socailmedia.repository;

import com.nicat.socailmedia.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findAccountByEmail(String email);
    Account findAccountByAccountIdAndStatus(Long id,Integer status);
    List<Account> findAllByStatus(Integer status);
}
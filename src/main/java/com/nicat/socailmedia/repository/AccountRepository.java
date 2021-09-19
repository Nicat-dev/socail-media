package com.nicat.socailmedia.repository;

import com.nicat.socailmedia.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
package com.nicat.socailmedia.security.service;

import com.nicat.socailmedia.exception.DomainNotFoundException;
import com.nicat.socailmedia.repository.AccountRepository;
import com.nicat.socailmedia.security.model.JwtUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("Search account by email : {}", email);
        return accountRepository.findAccountByEmail(email)
                .map(JwtUser::new)
                .orElseThrow(() -> new DomainNotFoundException("Account not found by email : " + email));
    }
}

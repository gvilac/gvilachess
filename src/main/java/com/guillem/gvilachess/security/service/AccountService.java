package com.guillem.gvilachess.security.service;

import com.guillem.gvilachess.exceptions.UserAlreadyExistsException;
import com.guillem.gvilachess.model.domain.user.Account;
import com.guillem.gvilachess.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.util.Collections.emptyList;

@Service("userDetailsService")
public class AccountService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    private PasswordEncoder passwordEncoder;

    private final AccountStatusUserDetailsChecker detailsChecker = new AccountStatusUserDetailsChecker();

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
        passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // Fetch the account corresponding to the given username
        Account account = accountRepository.findByUsername(username);

        User user = new User(account.getUsername(), account.getPassword(), account.isEnabled(), true, true, true, emptyList());

        detailsChecker.check(user);

        return user;
    }

    @Transactional
    public void createNewUser(Account account) throws UserAlreadyExistsException {
        if (accountRepository.usernameExists(account.getUsername())) {
            throw new UserAlreadyExistsException("User already exists");
        }

        account.setEnabled(true);
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        accountRepository.addAccount(account);
    }

}

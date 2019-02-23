package com.guillem.gvilachess.repository;

import com.guillem.gvilachess.model.domain.user.Account;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public interface AccountRepository {

	Account findByUsername(String username) throws UsernameNotFoundException;

	boolean usernameExists(String username);

	void addAccount(Account account);

	void findAll();

}
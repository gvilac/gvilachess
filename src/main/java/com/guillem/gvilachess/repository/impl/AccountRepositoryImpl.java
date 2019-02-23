package com.guillem.gvilachess.repository.impl;

import com.guillem.gvilachess.model.domain.user.Account;
import com.guillem.gvilachess.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AccountRepositoryImpl extends BasicRepository implements AccountRepository {

    private static final String KEY = "Account";

    @Autowired
    public AccountRepositoryImpl(RedisTemplate<String, Object> redisTemplate) {
        super(redisTemplate);
    }

    @Override
    public Account findByUsername(String username) throws UsernameNotFoundException {
        return Optional.ofNullable((Account) hashOperations.get(KEY, username)).orElseThrow(() -> new UsernameNotFoundException("User " + username + " not exists."));
    }

    @Override
    public boolean usernameExists(String username) {
        return Optional.ofNullable((Account) hashOperations.get(KEY, username)).isPresent();
    }

    @Override
    public void addAccount(Account account) {
        hashOperations.put(KEY, account.getUsername(), account);
    }

    @Override
    public void findAll() {
        hashOperations.entries(KEY);
    }

}

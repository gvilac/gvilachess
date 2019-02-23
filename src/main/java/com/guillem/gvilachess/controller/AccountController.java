package com.guillem.gvilachess.controller;

import com.guillem.gvilachess.controller.request.AccountRequest;
import com.guillem.gvilachess.controller.response.Response;
import com.guillem.gvilachess.exceptions.UserAlreadyExistsException;
import com.guillem.gvilachess.model.domain.user.Account;
import com.guillem.gvilachess.security.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by guillem on 16/02/2019.
 */
@RestController
@RequestMapping("api/account")
public class AccountController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountController.class);

    private AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/create")
    public Response createAccount(@Valid @RequestBody AccountRequest accountRequest) {
        Account account = Account.builder()
                .username(accountRequest.getUsername())
                .password(accountRequest.getPassword()).build();
        try {
            accountService.createNewUser(account);
        } catch (UserAlreadyExistsException e) {
            LOGGER.warn("Warning: " + e);
            return Response.builder().message(e.getMessage()).build();
        }
        return new Response();
    }

}

package com.guillem.gvilachess.controller.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Data
public class AccountRequest {

    @NotNull
    @Size(min = 2, message = "Name should have atleast 2 characters")
    private String username;

    @NotNull
    @Size(min=7, message="Password should have atleast 7 characters")
    private String password;


}

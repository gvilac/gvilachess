package com.guillem.gvilachess.controller.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by guillem on 16/02/2019.
 */
@Data
public class CreateGameRequest {

    @NotNull
    @Size(min = 2, message = "Name should have atleast 2 characters")
    String whitePlayerUsername;

    @NotNull
    @Size(min = 2, message = "Name should have atleast 2 characters")
    String blackPlayerUsername;
}

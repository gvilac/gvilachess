package com.guillem.gvilachess.controller.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by guillem on 16/02/2019.
 */
@Data
public class MovementRequest {

    @NotNull
    @Pattern(regexp = "[A-H][1-8]")
    private String fromPosition;

    @NotNull
    @Pattern(regexp = "[A-H][1-8]")
    private String toPosition;

}

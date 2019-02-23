package com.guillem.gvilachess.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by guillem on 16/02/2019.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Response<T> {

    private String message;
    private String stackTrace;
    private T object;


}

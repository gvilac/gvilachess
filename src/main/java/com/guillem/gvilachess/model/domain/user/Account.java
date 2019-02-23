package com.guillem.gvilachess.model.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by guillem on 16/02/2019.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account implements Serializable {

    private static final long serialVersionUID = 8412787803707077777L;
    private String id;
    private String username;
    private String password;
    private boolean enabled;

    private String gameId;

}

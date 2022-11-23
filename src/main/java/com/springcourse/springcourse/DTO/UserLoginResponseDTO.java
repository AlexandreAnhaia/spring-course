package com.springcourse.springcourse.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class UserLoginResponseDTO implements Serializable {

    private String token;
    private Long expireIn;
    private String tokenProvider;
}

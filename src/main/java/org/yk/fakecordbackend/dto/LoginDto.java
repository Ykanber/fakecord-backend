package org.yk.fakecordbackend.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class LoginDto {

    private String username;
    private String password;
}

package org.yk.fakecordbackend.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class FakecordUserDto {

    private String username;
    private String password;
    private String email;

}

package org.yk.fakecordbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.yk.fakecordbackend.entity.FakecordServer;
import org.yk.fakecordbackend.entity.FakecordUser;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class RegisterToServerDto {

    private FakecordUser fakecordUser;
    private FakecordServer fakecordServer;
}

package org.yk.fakecordbackend.mapper;

import org.mapstruct.Mapper;
import org.yk.fakecordbackend.dto.FakecordUserDto;
import org.yk.fakecordbackend.entity.FakecordUser;

@Mapper(componentModel = "spring")
public interface FakecordUserMapper {

    FakecordUser toEntity(FakecordUserDto fakecordUserDto);

}

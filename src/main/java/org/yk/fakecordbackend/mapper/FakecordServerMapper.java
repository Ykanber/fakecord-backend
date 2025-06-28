package org.yk.fakecordbackend.mapper;

import org.mapstruct.Mapper;
import org.yk.fakecordbackend.dto.ServerCreationDto;
import org.yk.fakecordbackend.dto.ServerLogoDto;
import org.yk.fakecordbackend.entity.FakecordServer;

@Mapper(componentModel = "spring")
public interface FakecordServerMapper {

    FakecordServer toEntity(ServerCreationDto serverCreationDto);

    ServerLogoDto toDto(FakecordServer fakecordServer);

}

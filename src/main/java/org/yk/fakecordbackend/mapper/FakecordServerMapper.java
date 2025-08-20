package org.yk.fakecordbackend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.yk.fakecordbackend.dto.ServerCreationDto;
import org.yk.fakecordbackend.dto.ServerDto;
import org.yk.fakecordbackend.entity.FakecordServer;

@Mapper(componentModel = "spring")
public interface FakecordServerMapper {

  FakecordServer toEntity(ServerCreationDto serverCreationDto);

  @Mapping(target = "serverId", source = "id")
  ServerDto toDto(FakecordServer fakecordServer);
}

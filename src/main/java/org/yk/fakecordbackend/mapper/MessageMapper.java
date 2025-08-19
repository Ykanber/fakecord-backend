package org.yk.fakecordbackend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.yk.fakecordbackend.dto.MessageCreationDto;
import org.yk.fakecordbackend.dto.MessageDto;
import org.yk.fakecordbackend.entity.FakecordUser;
import org.yk.fakecordbackend.entity.Message;

@Mapper(componentModel = "spring")
public interface MessageMapper {

  Message toEntity(MessageCreationDto messageCreationDto);

  @Mapping(target = "author", source = "sender", qualifiedByName = "userToUsername")
  MessageDto toDto(Message message);

  @Named("userToUsername")
  default String userToUsername(FakecordUser user) {
    return user.getUsername();
  }
}

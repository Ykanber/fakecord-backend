package org.yk.fakecordbackend.mapper;

import org.mapstruct.Mapper;
import org.yk.fakecordbackend.dto.ChannelCreationDto;
import org.yk.fakecordbackend.entity.MessageChannel;

@Mapper(componentModel = "spring")
public interface MessageChannelMapper {

    MessageChannel toEntity(ChannelCreationDto channelCreationDto);

    ChannelCreationDto toDto(MessageChannel messageChannel);


}

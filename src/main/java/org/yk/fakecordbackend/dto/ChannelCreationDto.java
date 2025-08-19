package org.yk.fakecordbackend.dto;

import lombok.Data;

@Data
public class ChannelCreationDto {
  long serverId;
  String channelName;
}

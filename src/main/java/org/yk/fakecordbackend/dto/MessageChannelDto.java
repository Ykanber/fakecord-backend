package org.yk.fakecordbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageChannelDto {
  long serverId;
  long channelId;
  String channelName;
}

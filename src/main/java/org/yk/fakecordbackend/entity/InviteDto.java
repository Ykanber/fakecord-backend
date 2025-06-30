package org.yk.fakecordbackend.entity;

import java.time.Duration;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class InviteDto {
  private Long serverId;
  private Duration hours = Duration.ofHours(24);
}

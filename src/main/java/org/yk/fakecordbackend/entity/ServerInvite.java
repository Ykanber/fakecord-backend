package org.yk.fakecordbackend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class ServerInvite {

  @ToString.Exclude @EqualsAndHashCode.Exclude @ManyToOne FakecordServer server;

  @Id @GeneratedValue private Long id;

  @Column(unique = true, nullable = false)
  private String code;

  private LocalDateTime createdAt;

  private LocalDateTime expiresAt;
}

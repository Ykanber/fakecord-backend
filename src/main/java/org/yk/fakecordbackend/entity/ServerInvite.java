package org.yk.fakecordbackend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Data;

@Entity
@Data
public class ServerInvite {
  @ManyToOne FakecordServer server;

  @Id @GeneratedValue private Long id;

  @Column(unique = true, nullable = false)
  private String code;

  private LocalDateTime createdAt;

  private LocalDateTime expiresAt;
}

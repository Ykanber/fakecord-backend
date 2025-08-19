package org.yk.fakecordbackend.entity;

import jakarta.persistence.*;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
public class MessageChannel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long channelId;

  private String channelName;

  @ManyToOne(cascade = CascadeType.ALL)
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private FakecordServer server;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private Set<Message> messages;
}

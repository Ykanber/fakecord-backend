package org.yk.fakecordbackend.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
public class FakecordServer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  private String name;

  private String logoUrl;

  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @OneToMany(mappedBy = "server", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<MessageChannel> messageChannel = new HashSet<>();

  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @OneToMany(mappedBy = "server", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Membership> memberships = new HashSet<>();
}

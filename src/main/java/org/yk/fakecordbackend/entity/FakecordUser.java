package org.yk.fakecordbackend.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@Entity
@Data
public class FakecordUser {

  @Id private String email;

  @Column(unique = true)
  private String username;

  private String password;

  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Membership> memberships = new HashSet<>();
}

package org.yk.fakecordbackend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "membership")
@Data
public class Membership {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "server_id", nullable = false)
    private FakecordServer server;

    @ManyToOne
    @JoinColumn(name = "username", nullable = false)
    private FakecordUser user;

    private String role;
    private String nickname;
    private LocalDateTime joinedAt = LocalDateTime.now();

}

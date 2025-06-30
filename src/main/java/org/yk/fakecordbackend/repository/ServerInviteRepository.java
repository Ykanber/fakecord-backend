package org.yk.fakecordbackend.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.yk.fakecordbackend.entity.ServerInvite;

public interface ServerInviteRepository extends JpaRepository<ServerInvite, Long> {
  Optional<ServerInvite> findByCode(String code);
}

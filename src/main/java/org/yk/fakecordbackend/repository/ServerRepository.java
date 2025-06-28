package org.yk.fakecordbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.yk.fakecordbackend.entity.FakecordServer;

public interface ServerRepository extends JpaRepository<FakecordServer, Long> {
}

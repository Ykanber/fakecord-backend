package org.yk.fakecordbackend.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.yk.fakecordbackend.entity.FakecordUser;

import java.util.Optional;

public interface UserRepository extends JpaRepository<FakecordUser, Long> {
    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    Optional<FakecordUser> findByUsername(String username);

    FakecordUser findByEmail(String email);

}



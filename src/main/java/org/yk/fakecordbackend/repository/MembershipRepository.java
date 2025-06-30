package org.yk.fakecordbackend.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.yk.fakecordbackend.entity.FakecordServer;
import org.yk.fakecordbackend.entity.FakecordUser;
import org.yk.fakecordbackend.entity.Membership;

public interface MembershipRepository extends JpaRepository<Membership, Long> {
    List<Membership> findAllByUser(FakecordUser user);

    boolean existsByServerAndUser(FakecordServer server, FakecordUser user);
}

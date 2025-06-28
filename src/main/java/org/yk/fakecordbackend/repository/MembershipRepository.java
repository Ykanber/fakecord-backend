package org.yk.fakecordbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.yk.fakecordbackend.entity.FakecordUser;
import org.yk.fakecordbackend.entity.Membership;

import java.util.List;

public interface MembershipRepository extends JpaRepository<Membership, Long> {
    List<Membership> findAllByUser(FakecordUser user);
}

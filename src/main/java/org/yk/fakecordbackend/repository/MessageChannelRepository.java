package org.yk.fakecordbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yk.fakecordbackend.entity.MessageChannel;

@Repository
public interface MessageChannelRepository extends JpaRepository<MessageChannel, Long> {}

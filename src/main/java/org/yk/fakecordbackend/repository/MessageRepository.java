package org.yk.fakecordbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yk.fakecordbackend.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {}

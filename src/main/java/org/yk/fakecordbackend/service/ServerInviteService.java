package org.yk.fakecordbackend.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.yk.fakecordbackend.entity.FakecordServer;
import org.yk.fakecordbackend.entity.FakecordUser;
import org.yk.fakecordbackend.entity.Membership;
import org.yk.fakecordbackend.entity.ServerInvite;
import org.yk.fakecordbackend.repository.MembershipRepository;
import org.yk.fakecordbackend.repository.ServerInviteRepository;
import org.yk.fakecordbackend.repository.ServerRepository;
import org.yk.fakecordbackend.repository.UserRepository;

@Service
public class ServerInviteService {

  private final ServerInviteRepository serverInviteRepository;
  private final ServerRepository serverRepository;
  private final UserRepository userRepository;
  private final MembershipRepository membershipRepository;

  public ServerInviteService(
      ServerInviteRepository serverInviteRepository,
      ServerRepository serverRepository,
      UserRepository userRepository,
      MembershipRepository membershipRepository) {
    this.serverInviteRepository = serverInviteRepository;
    this.serverRepository = serverRepository;
    this.userRepository = userRepository;
    this.membershipRepository = membershipRepository;
  }

  public String createInvite(Long serverId, Duration expireTime) {
    FakecordServer server =
        serverRepository
            .findById(serverId)
            .orElseThrow(() -> new RuntimeException("Server not found"));

    String code = UUID.randomUUID().toString().substring(0, 8);
    ServerInvite serverInvite = new ServerInvite();
    serverInvite.setCode(code);
    serverInvite.setServer(server);
    serverInvite.setCreatedAt(LocalDateTime.now());
    serverInvite.setExpiresAt(LocalDateTime.now().plus(expireTime));

    serverInviteRepository.save(serverInvite);
    return code;
  }

  public void joinServerWithInvite(String inviteCode, Long userId) {
    ServerInvite serverInvite =
        serverInviteRepository
            .findByCode(inviteCode)
            .orElseThrow(() -> new RuntimeException("Invalid invite code"));

    if (serverInvite.getExpiresAt().isBefore(LocalDateTime.now())) {
      throw new RuntimeException("Invite expired");
    }
    FakecordServer server = serverInvite.getServer();
    FakecordUser user =
        userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

    if (membershipRepository.existsByServerAndUser(server, user)) {
      throw new RuntimeException("User Already Registered");
    }
    Membership membership = new Membership();
    membership.setUser(user);
    membership.setServer(server);
    membership.setRole("User");
    membershipRepository.save(membership);
  }
}

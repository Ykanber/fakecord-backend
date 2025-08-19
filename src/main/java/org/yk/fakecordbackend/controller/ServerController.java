package org.yk.fakecordbackend.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.yk.fakecordbackend.dto.ChannelCreationDto;
import org.yk.fakecordbackend.dto.ServerCreationDto;
import org.yk.fakecordbackend.entity.InviteDto;
import org.yk.fakecordbackend.service.MembershipService;
import org.yk.fakecordbackend.service.ServerInviteService;
import org.yk.fakecordbackend.service.ServerService;

@RestController
@RequestMapping("api/server")
@AllArgsConstructor
@Slf4j
public class ServerController {

  private final MembershipService membershipService;
  private final ServerService serverService;
  private final ServerInviteService serverInviteService;

  @GetMapping("/registeredServers")
  public ResponseEntity<?> getServers(Authentication authentication) {
    String username = authentication.getName();
    log.info("username = {}", username);
    return ResponseEntity.ok().body(membershipService.getServers(username));
  }

  @GetMapping("/{serverId}/channels")
  public ResponseEntity<?> getServerChannels(
      @PathVariable int serverId, Authentication authentication) {
    log.info(String.valueOf(serverId));
    return ResponseEntity.ok().body(serverService.getServerChannels(serverId));
  }

  @PostMapping()
  public ResponseEntity<?> createServer(
      @RequestBody ServerCreationDto serverCreationDto, Authentication authentication) {
    log.info(serverCreationDto.toString());
    String username = authentication.getName();
    log.info(username);
    try {
      serverService.createServer(serverCreationDto, username);
      return ResponseEntity.ok("Server has been created successfully");
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body("Server name is invalid!");
    }
  }

  @PostMapping("/channel")
  public ResponseEntity<?> createChannel(
      @RequestBody ChannelCreationDto channelCreationDto, Authentication authentication) {
    log.info(channelCreationDto.toString());
    String username = authentication.getName();
    serverService.createServerChannel(channelCreationDto);
    return ResponseEntity.ok("Server has been created successfully");
  }

  @PostMapping("/server-registration")
  public ResponseEntity<?> registerToServer(@RequestBody InviteDto inviteDto) {
    log.info(inviteDto.toString());
    return ResponseEntity.ok("Invite has been created successfully");
  }

  @PostMapping("/server/invite")
  public String createInvite(@RequestBody InviteDto inviteDto, Authentication authentication) {
    log.info(inviteDto.toString());
    try {
      return serverInviteService.createInvite(inviteDto.getServerId(), inviteDto.getHours());
    } catch (IllegalArgumentException e) {
      return "false";
    }
  }
}

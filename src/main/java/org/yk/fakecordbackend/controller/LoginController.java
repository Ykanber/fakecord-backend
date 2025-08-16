package org.yk.fakecordbackend.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.yk.fakecordbackend.dto.ServerCreationDto;
import org.yk.fakecordbackend.entity.InviteDto;
import org.yk.fakecordbackend.service.*;

@RestController
@RequestMapping("/api")
@Slf4j
@AllArgsConstructor
public class LoginController {

  private final ServerService serverService;
  private final MembershipService membershipService;
  private final ServerInviteService serverInviteService;


  @GetMapping("auth/me")
  public ResponseEntity<?> checkLogin(@AuthenticationPrincipal UserDetails userDetails) {
    if (userDetails == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    return ResponseEntity.ok(userDetails.getUsername());
  }

  @GetMapping("/registered-servers")
  public ResponseEntity<?> getServers(Authentication authentication) {
    String username = authentication.getName();
    log.info("username = {}", username);
    return ResponseEntity.ok().body(membershipService.getServers(username));
  }

  @PostMapping("/server")
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

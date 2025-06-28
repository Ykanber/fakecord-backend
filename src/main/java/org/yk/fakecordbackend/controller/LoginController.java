package org.yk.fakecordbackend.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.yk.fakecordbackend.dto.FakecordUserDto;
import org.yk.fakecordbackend.dto.LoginDto;
import org.yk.fakecordbackend.dto.ServerCreationDto;
import org.yk.fakecordbackend.dto.ServerRegistrationDto;
import org.yk.fakecordbackend.service.JwtService;
import org.yk.fakecordbackend.service.MembershipService;
import org.yk.fakecordbackend.service.ServerService;
import org.yk.fakecordbackend.service.UserService;

@RestController
@RequestMapping("/fakecordfrontend")
@Slf4j
public class LoginController {

  private final UserService userService;
  private final ServerService serverService;
  private final MembershipService membershipService;
  private final JwtService jwtService;
  private final AuthenticationManagerBuilder authenticationManagerBuilder;

  LoginController(
      UserService userService,
      ServerService serverService,
      MembershipService membershipService,
      JwtService jwtService,
      AuthenticationManagerBuilder authenticationManagerBuilder) {
    this.userService = userService;
    this.serverService = serverService;
    this.membershipService = membershipService;
    this.jwtService = jwtService;
    this.authenticationManagerBuilder = authenticationManagerBuilder;
  }

  @PostMapping("/login")
  public ResponseEntity<?> loginUser(
      @RequestBody LoginDto loginData, HttpServletResponse response) {
    log.info(loginData.toString());
    try {
      userService.loginUser(loginData);
      String token = jwtService.generateToken(loginData.getUsername());

      ResponseCookie cookie =
          ResponseCookie.from("token", token)
              .httpOnly(true)
              .secure(false)
              .maxAge(10 * 60 * 60)
              .sameSite("Strict")
              .build();

      response.addHeader("Set-Cookie", cookie.toString());
      log.info("token<UNK>{}", token);
      return ResponseEntity.ok(token);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email or username is invalid!");
    }
  }

  @PostMapping("/signup")
  public ResponseEntity<?> signupUser(@RequestBody FakecordUserDto user) {
    log.info(user.toString());
    try {
      userService.addUser(user);
      return ResponseEntity.ok("User has been signed up successfully");
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body("Email or username is invalid!");
    }
  }

  @GetMapping("/registered-servers")
  public ResponseEntity<?> getServers(Authentication authentication) {
    String username = authentication.getName();
    log.info("username = {}", username);
    return ResponseEntity.ok().body(membershipService.getServers(username));
  }

  @PostMapping("/registered-servers")
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
  public ResponseEntity<?> registerToServer(
      @RequestBody ServerRegistrationDto serverRegistrationDto) {
    log.info(serverRegistrationDto.toString());
    return null;
  }

  @GetMapping("/signup")
  public void test() {
    System.out.println("test");
  }
}

package org.yk.fakecordbackend.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.yk.fakecordbackend.dto.FakecordUserDto;
import org.yk.fakecordbackend.dto.LoginDto;
import org.yk.fakecordbackend.service.AuthService;
import org.yk.fakecordbackend.service.JwtService;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
@Slf4j
public class AuthController {

  private final AuthService authService;
  private final JwtService jwtService;

  @PostMapping("/register")
  public ResponseEntity<?> registerUser(@RequestBody FakecordUserDto user) {
    log.info(user.toString());
    try {
      authService.register(user);
      return ResponseEntity.ok("User has been signed up successfully");
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body("Email or username is invalid!");
    }
  }

  @PostMapping("/login")
  public ResponseEntity<?> loginUser(
      @RequestBody LoginDto loginData, HttpServletResponse response) {
    log.info(loginData.toString());
    try {
      authService.loginUser(loginData);
      String token = jwtService.generateToken(loginData.getUsername());

      ResponseCookie cookie =
          ResponseCookie.from("token", token)
              .httpOnly(true)
              .secure(false)
              .path("/")
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

  @GetMapping("auth/me")
  public ResponseEntity<?> checkLogin(@AuthenticationPrincipal UserDetails userDetails) {
    if (userDetails == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    return ResponseEntity.ok(userDetails.getUsername());
  }
}

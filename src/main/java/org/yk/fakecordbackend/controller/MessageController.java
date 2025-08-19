package org.yk.fakecordbackend.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.yk.fakecordbackend.dto.MessageCreationDto;
import org.yk.fakecordbackend.service.MessageService;

@RestController
@Slf4j
@RequestMapping("api/server/")
@AllArgsConstructor
public class MessageController {

  private final MessageService messageService;

  @GetMapping("/{serverId}/{channelId}")
  public ResponseEntity<?> getMessages(@PathVariable int serverId, @PathVariable int channelId) {
    log.info(serverId + " " + channelId);
    return ResponseEntity.ok(messageService.getMessages(serverId, channelId));
  }

  @PostMapping("/channel/message")
  public ResponseEntity<?> postMessage(
      @RequestBody MessageCreationDto messageCreationDto,
      @AuthenticationPrincipal UserDetails userDetails) {
    log.info(messageCreationDto.toString());
    messageService.postMessage(messageCreationDto, userDetails.getUsername());
    return ResponseEntity.ok().build();
  }
}

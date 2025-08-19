package org.yk.fakecordbackend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.yk.fakecordbackend.dto.MessageCreationDto;
import org.yk.fakecordbackend.dto.MessageDto;
import org.yk.fakecordbackend.entity.FakecordUser;
import org.yk.fakecordbackend.entity.Message;
import org.yk.fakecordbackend.entity.MessageChannel;
import org.yk.fakecordbackend.mapper.MessageMapper;
import org.yk.fakecordbackend.repository.MessageChannelRepository;
import org.yk.fakecordbackend.repository.MessageRepository;
import org.yk.fakecordbackend.repository.UserRepository;

@Service
@AllArgsConstructor
@Slf4j
public class MessageService {

  private final MessageRepository messageRepository;
  private final MessageChannelRepository messageChannelRepository;
  private final MessageMapper messageMapper;
  private final UserRepository userRepository;

  public List<MessageDto> getMessages(long serverId, long channelId) {
    MessageChannel channel =
        messageChannelRepository
            .findById(channelId)
            .orElseThrow(() -> new RuntimeException("<UNK>"));

    List<MessageDto> messageDtos = new ArrayList<>();

    channel
        .getMessages()
        .forEach(
            message -> {
              messageDtos.add(messageMapper.toDto(message));
            });
    return messageDtos;
  }

  public void postMessage(MessageCreationDto messageCreationDto, String sender) {

    Optional<MessageChannel> messageChannel =
        messageChannelRepository.findById(messageCreationDto.getChannelId());

    if (messageChannel.isEmpty()) throw new RuntimeException("<UNK>");

    Message message = messageMapper.toEntity(messageCreationDto);
    Optional<FakecordUser> user = userRepository.findByUsername(sender);

    if (user.isEmpty()) throw new RuntimeException("<UNK>");

    message.setSender(user.get());
    messageRepository.save(message);
    messageChannel.get().getMessages().add(message);
    messageChannelRepository.save(messageChannel.get());
  }
}

package org.yk.fakecordbackend.service;

import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.yk.fakecordbackend.dto.ChannelCreationDto;
import org.yk.fakecordbackend.dto.MessageChannelDto;
import org.yk.fakecordbackend.dto.ServerCreationDto;
import org.yk.fakecordbackend.entity.FakecordServer;
import org.yk.fakecordbackend.entity.FakecordUser;
import org.yk.fakecordbackend.entity.Membership;
import org.yk.fakecordbackend.entity.MessageChannel;
import org.yk.fakecordbackend.mapper.FakecordServerMapper;
import org.yk.fakecordbackend.mapper.MessageChannelMapper;
import org.yk.fakecordbackend.repository.MembershipRepository;
import org.yk.fakecordbackend.repository.MessageChannelRepository;
import org.yk.fakecordbackend.repository.ServerRepository;
import org.yk.fakecordbackend.repository.UserRepository;

@Service
@Slf4j
@AllArgsConstructor
public class ServerService {

  private final ServerRepository serverRepository;
  private final UserRepository userRepository;
  private final FakecordServerMapper fakecordServerMapper;
  private final MessageChannelMapper messageChannelMapper;
  private final MembershipRepository membershipRepository;
  private final MessageChannelRepository messageChannelRepository;

  public void createServer(ServerCreationDto serverCreationDto, String username) {
    log.info(fakecordServerMapper.toEntity(serverCreationDto).toString());

    FakecordServer fakecordServer = fakecordServerMapper.toEntity(serverCreationDto);
    serverRepository.save(fakecordServer);

    FakecordUser fakecordUser =
        userRepository
            .findByUsername(username)
            .orElseThrow(() -> new EntityNotFoundException("<UNK>"));

    Membership membership = new Membership();
    membership.setUser(fakecordUser);
    membership.setServer(fakecordServer);
    membershipRepository.save(membership);
  }

  public void createServerChannel(ChannelCreationDto channelCreationDto) {
    log.info(channelCreationDto.toString());
    FakecordServer fakecordServer =
        serverRepository.getReferenceById(channelCreationDto.getServerId());

    MessageChannel messageChannel = messageChannelMapper.toEntity(channelCreationDto);
    messageChannel.setServer(fakecordServer);
    messageChannelRepository.save(messageChannel);
    fakecordServer.getMessageChannel().add(messageChannel);
    serverRepository.save(fakecordServer);
  }

  public List<MessageChannelDto> getServerChannels(long serverId) {
    Optional<FakecordServer> userData = serverRepository.findById(serverId);
    if (userData.isPresent()) {
      List<MessageChannelDto> messageChannelDtoList = new ArrayList<>();
      userData
          .get()
          .getMessageChannel()
          .forEach(
              channel -> {
                messageChannelDtoList.add(
                    new MessageChannelDto(
                        channel.getServer().getId(),
                        channel.getChannelId(),
                        channel.getChannelName()));
              });
      return messageChannelDtoList;
    }
    return new ArrayList<>();
  }
}

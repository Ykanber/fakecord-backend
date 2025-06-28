package org.yk.fakecordbackend.service;


import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.yk.fakecordbackend.dto.ServerCreationDto;
import org.yk.fakecordbackend.entity.FakecordServer;
import org.yk.fakecordbackend.entity.FakecordUser;
import org.yk.fakecordbackend.entity.Membership;
import org.yk.fakecordbackend.mapper.FakecordServerMapper;
import org.yk.fakecordbackend.repository.MembershipRepository;
import org.yk.fakecordbackend.repository.ServerRepository;
import org.yk.fakecordbackend.repository.UserRepository;

@Service
@Slf4j
public class ServerService {

    private final ServerRepository serverRepository;
    private final UserRepository userRepository;
    private final FakecordServerMapper fakecordServerMapper;
    private final MembershipRepository membershipRepository;

    ServerService(ServerRepository serverRepository, FakecordServerMapper fakecordServerMapper, UserRepository userRepository, MembershipRepository membershipRepository) {
        this.serverRepository = serverRepository;
        this.fakecordServerMapper = fakecordServerMapper;
        this.userRepository = userRepository;
        this.membershipRepository = membershipRepository;
    }

    public void createServer(ServerCreationDto serverCreationDto, String username) {
        log.info(fakecordServerMapper.toEntity(serverCreationDto).toString());

        FakecordServer fakecordServer = fakecordServerMapper.toEntity(serverCreationDto);
        serverRepository.save(fakecordServer);

        FakecordUser fakecordUser = userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("<UNK>"));

        Membership membership = new Membership();
        membership.setUser(fakecordUser);
        membership.setServer(fakecordServer);
        membershipRepository.save(membership);
    }
}

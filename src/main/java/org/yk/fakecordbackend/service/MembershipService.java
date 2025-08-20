package org.yk.fakecordbackend.service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.yk.fakecordbackend.dto.ServerDto;
import org.yk.fakecordbackend.entity.FakecordUser;
import org.yk.fakecordbackend.mapper.FakecordServerMapper;
import org.yk.fakecordbackend.repository.MembershipRepository;
import org.yk.fakecordbackend.repository.UserRepository;

@Service
public class MembershipService {

    private final MembershipRepository membershipRepository;
    private final FakecordServerMapper fakecordServerMapper;
    private final UserRepository userRepository;

    MembershipService(MembershipRepository membershipRepository, FakecordServerMapper fakecordServerMapper, UserRepository userRepository) {
        this.membershipRepository = membershipRepository;
        this.fakecordServerMapper = fakecordServerMapper;
        this.userRepository = userRepository;
    }

    public List<ServerDto> getServers(String username) {
        FakecordUser user = userRepository.findByUsername(username).orElseThrow(
                () -> new EntityNotFoundException("<UNK>")
        );
        return membershipRepository.findAllByUser(user).stream().map(e ->
                fakecordServerMapper.toDto(e.getServer())
        ).collect(Collectors.toList());
    }

    public void registerToServer() {

    }
}

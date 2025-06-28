package org.yk.fakecordbackend.service;

import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.yk.fakecordbackend.dto.FakecordUserDto;
import org.yk.fakecordbackend.dto.LoginDto;
import org.yk.fakecordbackend.entity.FakecordUser;
import org.yk.fakecordbackend.exception.InvalidCredentialsException;
import org.yk.fakecordbackend.mapper.FakecordUserMapper;
import org.yk.fakecordbackend.repository.UserRepository;

@Service
@Slf4j
public class UserService implements UserDetailsService {

  private final UserRepository userRepository;
  private final FakecordUserMapper fakecordUserMapper;

  public UserService(UserRepository userRepository, FakecordUserMapper fakecordUserMapper) {
    this.userRepository = userRepository;
    this.fakecordUserMapper = fakecordUserMapper;
  }

  public void loginUser(LoginDto loginDto) {
    log.info(loginDto.toString());

    Optional<FakecordUser> userData = userRepository.findByUsername(loginDto.getUsername());

    userData.ifPresentOrElse(
        fakecordUser -> {
          if (!fakecordUser.getPassword().equals(loginDto.getPassword())) {
            throw new InvalidCredentialsException("Kullanıcı adı veya şifre hatalı");
          }
        },
        () -> {
          throw new InvalidCredentialsException("Kullanıcı adı veya şifre hatalı");
        });
  }

  public void addUser(FakecordUserDto userDto) {
    log.info(fakecordUserMapper.toEntity(userDto).toString());
    FakecordUser fakecordUser = fakecordUserMapper.toEntity(userDto);
    if (userRepository.existsByEmail(fakecordUser.getEmail())
        || userRepository.existsByUsername(fakecordUser.getUsername())) {
      throw new IllegalArgumentException("ID already exists!");
    }
    userRepository.save(fakecordUserMapper.toEntity(userDto));
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    FakecordUser userData =
        userRepository
            .findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("test"));

    return User.withUsername(username).password(userData.getPassword()).roles("USER").build();
  }
}

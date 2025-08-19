package org.yk.fakecordbackend.service;

import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.yk.fakecordbackend.dto.FakecordUserDto;
import org.yk.fakecordbackend.dto.LoginDto;
import org.yk.fakecordbackend.entity.FakecordUser;
import org.yk.fakecordbackend.exception.InvalidCredentialsException;
import org.yk.fakecordbackend.mapper.FakecordUserMapper;
import org.yk.fakecordbackend.repository.UserRepository;

@Service
@Slf4j
public class AuthService {

  private final UserRepository userRepository;
  private final FakecordUserMapper fakecordUserMapper;
  private final PasswordEncoder passwordEncoder;

  public AuthService(UserRepository userRepository, FakecordUserMapper fakecordUserMapper) {
    this.userRepository = userRepository;
    this.fakecordUserMapper = fakecordUserMapper;
    this.passwordEncoder = new BCryptPasswordEncoder();
  }

  public void loginUser(LoginDto loginDto) {
    log.info(loginDto.toString());
    Optional<FakecordUser> userData = userRepository.findByUsername(loginDto.getUsername());

    userData.ifPresentOrElse(
        fakecordUser -> {
          if (!passwordEncoder.matches(loginDto.getPassword(), fakecordUser.getPassword())) {
            throw new InvalidCredentialsException("Kullanıcı adı veya şifre hatalı");
          }
        },
        () -> {
          throw new InvalidCredentialsException("Kullanıcı adı veya şifre hatalı");
        });
  }

  public void register(FakecordUserDto userDto) {
    log.info(fakecordUserMapper.toEntity(userDto).toString());
    FakecordUser fakecordUser = fakecordUserMapper.toEntity(userDto);
    if (userRepository.existsByEmail(fakecordUser.getEmail())
        || userRepository.existsByUsername(fakecordUser.getUsername())) {
      throw new IllegalArgumentException("ID already exists!");
    }

    String encodedPassword = passwordEncoder.encode(fakecordUser.getPassword());
    userDto.setPassword(encodedPassword);
    userRepository.save(fakecordUserMapper.toEntity(userDto));
  }
}

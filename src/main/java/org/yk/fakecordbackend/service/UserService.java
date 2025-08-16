package org.yk.fakecordbackend.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.yk.fakecordbackend.entity.FakecordUser;
import org.yk.fakecordbackend.repository.UserRepository;

@Service
@Slf4j
@AllArgsConstructor
public class UserService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    FakecordUser userData =
        userRepository
            .findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("test"));

    return User.withUsername(username).password(userData.getPassword()).roles("USER").build();
  }
}

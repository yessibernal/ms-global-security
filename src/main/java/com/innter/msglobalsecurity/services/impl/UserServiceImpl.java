package com.innter.msglobalsecurity.services.impl;

import com.innter.msglobalsecurity.model.dtos.RolDto;
import com.innter.msglobalsecurity.model.dtos.UserSecurityDto;
import com.innter.msglobalsecurity.model.entities.UserEntity;
import com.innter.msglobalsecurity.model.repositories.UserRepository;
import com.innter.msglobalsecurity.services.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDetails getUserByUsername(String userName) {
        UserEntity user = userRepository.getUserWithRoles(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return UserSecurityDto.builder()
                .userName(user.getUserName())
                .password(user.getPassword())
                .roles(user.getUsersRoles().stream().map(r -> RolDto.builder().rolName(r.getRol().getRolName()).build()).collect(Collectors.toList()))
                .build();
    }
}

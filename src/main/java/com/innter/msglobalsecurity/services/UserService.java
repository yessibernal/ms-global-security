package com.innter.msglobalsecurity.services;

import com.innter.msglobalsecurity.model.entities.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {

    UserDetails getUserByUsername(String userName);
}

package com.innter.msglobalsecurity.services;

import com.innter.msglobalsecurity.model.dtos.RolDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface JwtService {

    String extractUserName(String token) throws NoSuchAlgorithmException, InvalidKeySpecException;

    String generateToken(UserDetails userDetails) throws NoSuchAlgorithmException, InvalidKeySpecException;

    boolean isTokenValid(String token) throws NoSuchAlgorithmException, InvalidKeySpecException;

    List<RolDto> extractScopes(String token) throws NoSuchAlgorithmException, InvalidKeySpecException;

    LocalDateTime extractDate(String token) throws NoSuchAlgorithmException, InvalidKeySpecException;
}

package com.innter.msglobalsecurity.services.impl;

import com.innter.msglobalsecurity.model.dtos.request.JwtRequest;
import com.innter.msglobalsecurity.model.dtos.response.JwtResponse;
import com.innter.msglobalsecurity.services.AuthenticationService;
import com.innter.msglobalsecurity.services.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public JwtResponse signIn(String requestAuthHeader) throws NoSuchAlgorithmException, InvalidKeySpecException {
        JwtRequest jwtRequest = parseBasicAuth(requestAuthHeader);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String newToken = jwtService.generateToken(userDetails);
        return JwtResponse.builder()
                .token(newToken)
                .expirationDate(jwtService.extractDate(newToken))
                .build();
    }

    private JwtRequest parseBasicAuth(String authHeader) {
        String basicAuth = authHeader.substring(6, authHeader.length());
        System.out.println(basicAuth);
        byte[] decodeBasicAuth = Base64.getDecoder().decode(basicAuth);
        System.out.println(decodeBasicAuth);
        String[] splitBasicAuth = (new String(decodeBasicAuth)).split(":");
        System.out.println(splitBasicAuth);
        System.out.println(splitBasicAuth[0]);
        System.out.println(splitBasicAuth[1]);
        return JwtRequest.builder()
                .username(splitBasicAuth[0])
                .password(splitBasicAuth[1])

                .build();

    }
}

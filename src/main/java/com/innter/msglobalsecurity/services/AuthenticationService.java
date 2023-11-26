package com.innter.msglobalsecurity.services;

import com.innter.msglobalsecurity.model.dtos.request.JwtRequest;
import com.innter.msglobalsecurity.model.dtos.response.JwtResponse;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface AuthenticationService {

    JwtResponse signIn(String requestAuthHeader) throws NoSuchAlgorithmException, InvalidKeySpecException;
}

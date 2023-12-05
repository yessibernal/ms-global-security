package com.innter.msglobalsecurity.controllers;

import com.innter.msglobalsecurity.model.dtos.request.SignUpRequest;
import com.innter.msglobalsecurity.model.dtos.response.JwtResponse;
import com.innter.msglobalsecurity.services.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/security")
public class SecurityController {

    private final AuthenticationService authenticationService;
    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> signIn(@RequestHeader("Authorization") String auth) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return ResponseEntity.ok(authenticationService.signIn(auth));
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody @Valid SignUpRequest request) {
        System.out.println(request);
        return ResponseEntity.ok("Ok");
    }

    @GetMapping("/health")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("OK");
    }
}

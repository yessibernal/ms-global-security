package com.innter.msglobalsecurity;

import com.innter.msglobalsecurity.model.dtos.RolDto;
import com.innter.msglobalsecurity.model.dtos.UserSecurityDto;
import com.innter.msglobalsecurity.services.impl.JwtServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class JwtServiceTest {

    @InjectMocks
    private JwtServiceImpl jwtService;

    @BeforeEach
    void setUp() {
        String jwtSigningKey = "413F4428472B4B6250655368566D5970337336763979244226452948404D6351";
        this.jwtService = new JwtServiceImpl();
    }

    @Test void getName() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkiLCJuYW1lIjoiSm9obiBEb2UiLCJpYXQiOjE1MTYyMzkwMjIsIm5vbWJyZSI6IlVyaWVsIn0.I1jami2WPODIkQaYfSzP9qVe-7PN_tHyzWxL9CHT0ys";
        String userName = jwtService.extractUserName(token);
        Assertions.assertEquals("123456789", userName);

    }

    @Test void getToken() throws NoSuchAlgorithmException, InvalidKeySpecException {
        List<RolDto> roles = new ArrayList<>();
        roles.add(RolDto.builder().rolName("ADMIN").build());
        roles.add(RolDto.builder().rolName("USER").build());
        roles.add(RolDto.builder().rolName("CHAPETE").build());
        UserSecurityDto user = UserSecurityDto.builder()
                .userName("Buero")
                .password("123456789")
                .roles(roles)
                .build();
        String jwt = jwtService.generateToken(user);
        System.out.println(jwt);
        Assertions.assertNotNull(jwt);
    }

    @Test void whenIsTokenValid() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzY29wZXMiOlsiQURNSU4iLCJVU0VSIiwiQ0hBUEVURSJdLCJzdWIiOiJCdWVybyIsImlhdCI6MTY5Mzg4OTY5MCwiZXhwIjoxNjkzODkxMTMwfQ.cXrglcbLhicbYtKBuY3aq6TW-W_pc0cB8iGSr4Tf8UM";
        boolean isTokenValid = jwtService.isTokenValid(token);
        Assertions.assertTrue(isTokenValid);
    }

    @Test void getRoles() throws NoSuchAlgorithmException, InvalidKeySpecException {
            String token = "eyJhbGciOiJIUzI1NiJ9.eyJzY29wZXMiOlsiQURNSU4iLCJVU0VSIiwiQ0hBUEVURSJdLCJzdWIiOiJCdWVybyIsImlhdCI6MTY5Mzk3Mzk1MSwiZXhwIjoxNjkzOTc1MzkxfQ.EcRv9BsYNDD5RCqqL7spJrhT_x6Kqf2_2quhciS7Djs";
        List<RolDto> scopes = jwtService.extractScopes(token);
        scopes.forEach(g -> System.out.println(g.getRolName()));
        Assertions.assertEquals(3, scopes.size());

    }
}

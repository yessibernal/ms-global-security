package com.innter.msglobalsecurity.configs.jwt;

import com.innter.msglobalsecurity.model.dtos.RolDto;
import com.innter.msglobalsecurity.model.dtos.UserSecurityDto;
import com.innter.msglobalsecurity.services.JwtService;
import io.jsonwebtoken.ClaimJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

@Component
@RequiredArgsConstructor
@Log4j2
public class SecurityFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String user;
        final List<RolDto> roles;
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }
            assert authHeader != null;
            jwt = authHeader.substring(7);
            user = jwtService.extractUserName(jwt);
            roles = jwtService.extractScopes(jwt);
            if (!user.isEmpty() && jwtService.isTokenValid(jwt) &&
                    SecurityContextHolder.getContext().getAuthentication() == null) {
                UserSecurityDto userDetails = UserSecurityDto.builder().userName(user).roles(roles).build();
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                context.setAuthentication(authToken);
                SecurityContextHolder.setContext(context);
            }
            filterChain.doFilter(request, response);
        } catch (ClaimJwtException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            log.error("Spring Security Filter Chain Exception:", e);
            resolver.resolveException(request, response, null, e);
        }

    }
}

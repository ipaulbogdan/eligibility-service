package com.idorasi.eligibility.controller;

import com.idorasi.eligibility.dto.LoginRequest;
import com.idorasi.eligibility.dto.RefreshToken;
import com.idorasi.eligibility.dto.TokenResponse;
import com.idorasi.eligibility.security.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class AuthController {

    private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;
    private JwtProvider jwtProvider;

    @PostMapping("/login")
    public TokenResponse login(@RequestBody LoginRequest loginRequest) {
        var authRequest = UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.username(), loginRequest.password());
        var auth = this.authenticationManager.authenticate(authRequest);

        SecurityContextHolder.getContext().setAuthentication(auth);
        return new TokenResponse(jwtProvider.generateToken(), jwtProvider.generateRefreshToken());
    }

    @PostMapping("/refresh")
    public TokenResponse refresh(@RequestBody RefreshToken refreshToken) {
        if (jwtProvider.validateJwtToken(refreshToken.refreshToken())) {
            var username = jwtProvider.extractUsername(refreshToken.refreshToken());
            var userDetails = userDetailsService.loadUserByUsername(username);

            var roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .toList();

            var claims = Map.of("role", roles);
            var token = jwtProvider.generateToken(claims, userDetails);

            return new TokenResponse(token, refreshToken.refreshToken());
        }

        return null;
    }

}

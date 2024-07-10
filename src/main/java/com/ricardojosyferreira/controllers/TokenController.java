package com.ricardojosyferreira.controllers;

import com.ricardojosyferreira.domain.Role;
import com.ricardojosyferreira.domain.User;
import com.ricardojosyferreira.domain.dto.LoginRequest;
import com.ricardojosyferreira.domain.dto.LoginResponse;
import com.ricardojosyferreira.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = {"http://localhost:4200", "https://528f-2804-229c-8200-22da-aa51-5f99-917a-d62c.ngrok-free.app/"})
@RequestMapping("/api/v1/login")
public class TokenController {

    private final JwtEncoder jwtEncoder;
    private final UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

    public TokenController(JwtEncoder jwtEncoder,
                           UserRepository userRepository,
                           BCryptPasswordEncoder passwordEncoder) {
        this.jwtEncoder = jwtEncoder;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {

        Optional<User> user = userRepository.findByUsername(loginRequest.username());

        if (user.isEmpty() || !user.get().isLoginCorrect(loginRequest, passwordEncoder)) {
            throw new BadCredentialsException("Credentials is invalid!");
        }

        Instant now = Instant.now();
        Long expiresIn = 3600L;
        String expiresAt = now.plusSeconds(expiresIn).toString();

        String scopes = user.get().getRoles()
                .stream()
                .map(Role::getName)
                .collect(Collectors.joining(" "));

        var claims = JwtClaimsSet.builder()
                .issuer("stock-backend")
                .subject(user.get().getId().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
                .claim("scope", scopes)
                .build();

        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return ResponseEntity.ok(new LoginResponse(jwtValue, expiresAt, loginRequest.username()));
    }
}
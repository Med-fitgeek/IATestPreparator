package com.fitgeek.IATestPreparator.services.impl;

import com.fitgeek.IATestPreparator.dtos.LoginRequestDto;
import com.fitgeek.IATestPreparator.dtos.LoginResponseDto;
import com.fitgeek.IATestPreparator.dtos.RegisterRequestDto;
import com.fitgeek.IATestPreparator.dtos.RegisterResponseDto;
import com.fitgeek.IATestPreparator.entities.User;
import com.fitgeek.IATestPreparator.entities.enums.Role;
import com.fitgeek.IATestPreparator.repositories.UserRepository;
import com.fitgeek.IATestPreparator.security.JwtUtil;
import com.fitgeek.IATestPreparator.services.AuthService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public RegisterResponseDto register(RegisterRequestDto registerRequestDto) {

        if (userRepository.existsByEmail(registerRequestDto.email()))
            throw new IllegalStateException ("Email already exists");

        if (userRepository.existsByUsername(registerRequestDto.username()))
            throw new IllegalStateException ("Username already exists");

        User user = User.builder()
                .username(registerRequestDto.username())
                .email(registerRequestDto.email())
                .password(passwordEncoder.encode(registerRequestDto.password()))
                .role(Role.USER)
                .build();

        User savedUser = userRepository.save(user);

        return new RegisterResponseDto(savedUser.getId(), savedUser.getUsername());
    }

    @Override
    public LoginResponseDto login(LoginRequestDto dto) {

        User user = userRepository.findByEmail(dto.email())
                .orElseThrow(() -> new IllegalStateException ("Invalid credentials"));

        if (!passwordEncoder.matches(dto.password(), user.getPassword())) {
            throw new IllegalStateException ("Invalid credentials");
        }

        String accessToken = jwtUtil.generateToken(new User(
                user.getUsername(),
                user.getEmail(),
                user.getRole())
        );

        // 5. Construction de la réponse
        // ➜ Jamais retourner l’entité User
        return new LoginResponseDto(
                accessToken,
                "Bearer",
                jwtUtil.getExpirationInSeconds()
        );
    }
    
}

package com.greenfoxacademy.ebayclone.security;

import com.greenfoxacademy.ebayclone.dtos.user.UserCreationDTO;
import com.greenfoxacademy.ebayclone.exeptions.user.PasswordInvalidException;
import com.greenfoxacademy.ebayclone.repositories.UserRepo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class JwtProviderService {
    private final JwtEncoder encoder;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsServiceImpl userDetailsService;

    private final UserRepo userRepo;


    public JwtProviderService(JwtEncoder encoder, PasswordEncoder passwordEncoder, UserDetailsServiceImpl userDetailsService, UserRepo userRepo) {
        this.encoder = encoder;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.userRepo = userRepo;
    }

    public String generateTokenByUserLoginRequest(UserCreationDTO userCreationDTO) throws UsernameNotFoundException, PasswordInvalidException {
        Instant now = Instant.now();
        long expiry = (24 * 60 * 60);
        var authUser = this.userDetailsService.loadUserByUsername(userCreationDTO.getUsername());
        var user = this.userRepo.findUserByUsername(authUser.getUsername()).get();
        if (!this.passwordEncoder.matches(userCreationDTO.getPassword(), authUser.getPassword())) {
            throw new PasswordInvalidException("Invalid username or password.");
        }
        String scope = authUser.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        Map<String, Object> claims = new HashMap<>();
        claims.put("scope", scope);
        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiry))
                .subject(authUser.getUsername())
                .id(String.valueOf(user.getId().intValue()))
                .claims((map) -> map.putAll(claims))
                .build();
        return this.encoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();
    }
}

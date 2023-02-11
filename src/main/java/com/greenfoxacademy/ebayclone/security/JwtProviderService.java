package com.greenfoxacademy.ebayclone.security;

import com.greenfoxacademy.ebayclone.dtos.user.UserCreationDTO;
import com.greenfoxacademy.ebayclone.exeptions.user.PasswordInvalidException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.stream.Collectors;

@Service
public class JwtProviderService {
    private final JwtEncoder encoder;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsServiceImpl userDetailsService;


    public JwtProviderService(JwtEncoder encoder, PasswordEncoder passwordEncoder, UserDetailsServiceImpl userDetailsService) {
        this.encoder = encoder;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    public String generateTokenByUserLoginRequest(UserCreationDTO userCreationDTO) throws UsernameNotFoundException, PasswordInvalidException {
        Instant now = Instant.now();
        long expiry = (24 * 60 * 60);
        var user = this.userDetailsService.loadUserByUsername(userCreationDTO.getUsername());
        if (!this.passwordEncoder.matches(userCreationDTO.getPassword(), user.getPassword())) {
            throw new PasswordInvalidException("Invalid username or password.");
        }
        String scope = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiry))
                .subject(user.getUsername())
                .claim("scope", scope)
                .build();
        return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}

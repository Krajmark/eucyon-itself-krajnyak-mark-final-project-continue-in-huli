package com.greenfoxacademy.ebayclone.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;

    public UserDetailsServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        return new org.springframework.security.core.userdetails.User(
                "admin",
                this.passwordEncoder.encode("asdasd"),
                // Needed to add "ROLE_" because the sec context automatically ads it when going through the filterChain method
                List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
    }

}

package com.greenfoxacademy.ebayclone.security;

import com.greenfoxacademy.ebayclone.models.User;
import com.greenfoxacademy.ebayclone.repositories.UserRepo;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepo userRepo;

    public UserDetailsServiceImpl(
            UserRepo userRepo
    ) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userCandidate = this.userRepo.findUserByUsername(username);
        if (userCandidate.isEmpty()) {
            throw new UsernameNotFoundException("No such user!");
        }
        return new UserDetailsImpl(
                userCandidate.get().getUsername(),
                userCandidate.get().getPassword(),
                // Needed to add "ROLE_" because the sec context automatically ads it when going through the filterChain method
                List.of(new SimpleGrantedAuthority("ROLE_" + userCandidate.get().getClass().getSimpleName().toUpperCase())));
    }

}

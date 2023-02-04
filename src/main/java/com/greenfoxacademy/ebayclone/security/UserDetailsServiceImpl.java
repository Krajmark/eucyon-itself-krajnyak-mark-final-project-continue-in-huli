package com.greenfoxacademy.ebayclone.security;

import com.greenfoxacademy.ebayclone.models.Admin;
import com.greenfoxacademy.ebayclone.models.Buyer;
import com.greenfoxacademy.ebayclone.models.Seller;
import com.greenfoxacademy.ebayclone.models.User;
import com.greenfoxacademy.ebayclone.repositories.AdminRepo;
import com.greenfoxacademy.ebayclone.repositories.BuyerRepo;
import com.greenfoxacademy.ebayclone.repositories.SellerRepo;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final BuyerRepo buyerRepo;
    private final SellerRepo sellerRepo;
    private final AdminRepo adminRepo;

    public UserDetailsServiceImpl(
            BuyerRepo buyerRepo,
            SellerRepo sellerRepo,
            AdminRepo adminRepo) {
        this.buyerRepo = buyerRepo;
        this.sellerRepo = sellerRepo;
        this.adminRepo = adminRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = null;
        Optional<Buyer> buyerCandidate = this.buyerRepo.findBuyerByUsername(username);
        Optional<Seller> sellerCandidate = this.sellerRepo.findSellerByUsername(username);
        Optional<Admin> adminCandidate = this.adminRepo.findAdminByUsername(username);
        if (buyerCandidate.isPresent()) {
            user = buyerCandidate.get();
        } else if (sellerCandidate.isPresent()) {
            user = sellerCandidate.get();
        } else if (adminCandidate.isPresent()) {
            user = adminCandidate.get();
        } else {
            throw new UsernameNotFoundException("No such user!");
        }
        return new UserDetailsImpl(
                user.getUsername(),
                user.getPassword(),
                // Needed to add "ROLE_" because the sec context automatically ads it when going through the filterChain method
                List.of(new SimpleGrantedAuthority("ROLE_" + user.getClass().getSimpleName().toUpperCase())));
    }

}

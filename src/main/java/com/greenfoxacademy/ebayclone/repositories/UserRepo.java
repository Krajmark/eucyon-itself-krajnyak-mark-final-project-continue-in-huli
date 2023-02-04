package com.greenfoxacademy.ebayclone.repositories;

import com.greenfoxacademy.ebayclone.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends CrudRepository<User, Integer> {
    Optional<User> findUserByUsername(String username);

    Boolean existsByUsername(String username);
}

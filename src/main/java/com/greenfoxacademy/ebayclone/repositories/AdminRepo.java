package com.greenfoxacademy.ebayclone.repositories;

import com.greenfoxacademy.ebayclone.models.Admin;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepo extends CrudRepository<Admin, Integer> {
    Optional<Admin> findAdminByUsername(String username);
}

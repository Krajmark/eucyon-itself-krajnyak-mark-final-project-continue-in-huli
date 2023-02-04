package com.greenfoxacademy.ebayclone.repositories;

import com.greenfoxacademy.ebayclone.models.Buyer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BuyerRepo extends CrudRepository<Buyer, Integer> {
    Optional<Buyer> findBuyerByUsername(String username);
}

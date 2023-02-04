package com.greenfoxacademy.ebayclone.repositories;

import com.greenfoxacademy.ebayclone.models.Seller;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerRepo extends CrudRepository<Seller, Integer> {
}

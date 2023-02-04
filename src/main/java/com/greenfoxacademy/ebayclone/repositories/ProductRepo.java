package com.greenfoxacademy.ebayclone.repositories;

import com.greenfoxacademy.ebayclone.models.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends CrudRepository<Product, Integer> {
}

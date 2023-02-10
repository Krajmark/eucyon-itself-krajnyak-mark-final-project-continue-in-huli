package com.greenfoxacademy.ebayclone.repositories;

import com.greenfoxacademy.ebayclone.models.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends PagingAndSortingRepository<Product, Integer>, CrudRepository<Product, Integer> {
}

package com.eauction.readservice.repository;

import com.eauction.readservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {

    public List<Product> findByUserEmail(String email);
}

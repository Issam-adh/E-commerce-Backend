package com.jobgate.demo.dao;

import com.jobgate.demo.Models.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProduct extends JpaRepository<Products, Long> {

}

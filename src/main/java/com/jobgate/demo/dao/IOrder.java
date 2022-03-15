package com.jobgate.demo.dao;

import com.jobgate.demo.Models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrder extends JpaRepository<Order, Long> {
}

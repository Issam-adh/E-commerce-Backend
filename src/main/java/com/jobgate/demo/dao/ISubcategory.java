package com.jobgate.demo.dao;

import com.jobgate.demo.Models.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISubcategory extends JpaRepository<SubCategory, Long> {
}

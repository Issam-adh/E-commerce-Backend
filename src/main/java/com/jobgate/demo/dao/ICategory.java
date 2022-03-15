package com.jobgate.demo.dao;

import com.jobgate.demo.Models.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.relational.core.sql.Select;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICategory extends JpaRepository<Category, Long> {


    @Query("Select c from Category c where c.name = :name and lower(c.name) = :name")
    List<Category> getAllByName(@Param("name") String name);

    @Query("select c from Category c ")
    Page<Category> getAllCategory(Pageable pageable);

    @Query("select c from Category c where c.name like :x")
    Page<Category> getAllCategoryByfilter(@Param("x") String name, Pageable pageable);
}

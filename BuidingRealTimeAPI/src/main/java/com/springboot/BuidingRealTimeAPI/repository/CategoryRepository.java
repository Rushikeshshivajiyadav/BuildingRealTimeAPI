package com.springboot.BuidingRealTimeAPI.repository;

import com.springboot.BuidingRealTimeAPI.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {


}

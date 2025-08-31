package com.petracker.framework.repository;

import com.petracker.framework.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
    public Category findByName(String categoryName);
}

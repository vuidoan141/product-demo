package com.vuidoan.product.repository;

import com.vuidoan.product.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    List<Category> findAllByDeletedFalse();
    Page<Category> findAllByDeletedFalse(Pageable pageable);
    Category findByIdAndDeletedFalse(int id);
    int countByNameAndIdNot(String name, int id);
}

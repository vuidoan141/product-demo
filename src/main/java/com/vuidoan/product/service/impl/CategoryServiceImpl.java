package com.vuidoan.product.service.impl;

import com.vuidoan.product.entity.Category;
import com.vuidoan.product.repository.CategoryRepository;
import com.vuidoan.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAllByDeletedFalse();
    }

    @Override
    public Page<Category> findAll(Pageable pageable) {
        return categoryRepository.findAllByDeletedFalse(pageable);
    }

    @Override
    public Category findById(int id) {
        return categoryRepository.findByIdAndDeletedFalse(id);
    }

    @Override
    public void save(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public void deleteById(int id) {
        Category category = this.findById(id);
        if (category != null) {
            category.setDeleted(true);
            this.save(category);
        }
    }
}

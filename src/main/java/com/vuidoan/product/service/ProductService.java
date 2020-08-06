package com.vuidoan.product.service;

import com.vuidoan.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface ProductService {
    Page<Product> findAll(Map<String, String> queryMap, Pageable pageable);
    Product findById(int id);
    void save(Product product);
    void deleteById(int id);
}

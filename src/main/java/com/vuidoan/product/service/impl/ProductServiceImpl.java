package com.vuidoan.product.service.impl;

import com.vuidoan.product.entity.Product;
import com.vuidoan.product.repository.ProductRepository;
import com.vuidoan.product.specification.ProductSpecification;
import com.vuidoan.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;

    @Override
    public Page<Product> findAll(Map<String, String> queryMap, Pageable pageable) {
        return productRepository.findAll(ProductSpecification.getCombineQuery(queryMap), pageable);
    }

    @Override
    public Product findById(int id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Product product) {
        productRepository.save(product);
    }

    @Override
    public void deleteById(int id) {
        Product product = this.findById(id);
        if(product!=null) {
            product.setDeleted(true);
            this.save(product);
        }
    }
}

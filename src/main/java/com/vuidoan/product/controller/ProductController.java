package com.vuidoan.product.controller;

import com.vuidoan.product.dto.ProductDTO;
import com.vuidoan.product.entity.Product;
import com.vuidoan.product.exception.ResourceNotFoundException;
import com.vuidoan.product.service.CategoryService;
import com.vuidoan.product.service.ProductService;
import com.vuidoan.product.validation.ValidatorHelper;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {
    static Logger logger = LoggerFactory.getLogger(ProductController.class);
    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<?> createProduct(@Validated @RequestBody ProductDTO productDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = ValidatorHelper.returnValidationErrors(bindingResult);
            return new ResponseEntity<List<String>>(errors, HttpStatus.BAD_REQUEST);
        }
        productService.save(modelMapper.map(productDTO, Product.class));
        return new ResponseEntity<ProductDTO>(productDTO, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable("id") int id, @Validated @RequestBody ProductDTO productDTO,
                                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = ValidatorHelper.returnValidationErrors(bindingResult);
            return new ResponseEntity<List<String>>(errors, HttpStatus.BAD_REQUEST);
        }
        Product product = productService.findById(productDTO.getId());
        if(product==null) {
            throw new ResourceNotFoundException("ProductId is not existed");
        }
        productService.save(modelMapper.map(productDTO, Product.class));
        return new ResponseEntity<ProductDTO>(productDTO, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                     @RequestParam(value = "size", defaultValue = "10") int size,
                                     @RequestParam(value = "order", defaultValue = "ASC") String order,
                                     @RequestParam(value = "sort", defaultValue = "name") String sort,
                                     @RequestParam Map<String, String> queryMap) {
        try {
            Sort sortOption = Sort.by(order.equalsIgnoreCase("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC, sort);
            Page<Product> products = productService.findAll(queryMap, PageRequest.of(page, size, sortOption));
            Page<ProductDTO> productDTOs = products.map(e -> modelMapper.map(e, ProductDTO.class));
            return new ResponseEntity<Page<ProductDTO>>(productDTOs, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.toString());
            return new ResponseEntity<String>("Bad request", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") int id) {
        Product product = productService.findById(id);
        if (product == null) {
            throw new ResourceNotFoundException("Product Not Found");
        }
        return new ResponseEntity<Product>(product, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") int id) {
        productService.deleteById(id);
        return ResponseEntity.ok("Delete Product Successfully!");
    }
}

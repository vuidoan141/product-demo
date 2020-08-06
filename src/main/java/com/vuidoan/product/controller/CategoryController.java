package com.vuidoan.product.controller;

import com.vuidoan.product.dto.CategoryDTO;
import com.vuidoan.product.exception.ResourceNotFoundException;
import com.vuidoan.product.service.CategoryService;
import com.vuidoan.product.entity.Category;
import com.vuidoan.product.validation.ValidatorHelper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<?> findAllCategories() {
        List<Category> categories = categoryService.findAll();
        List<CategoryDTO> categoryDTOs =
                categories.stream().map(e -> modelMapper.map(e, CategoryDTO.class)).collect(Collectors.toList());
        return new ResponseEntity<List<CategoryDTO>>(categoryDTOs, HttpStatus.OK);
    }

    @GetMapping(params = {"page", "size"})
    public ResponseEntity<?> findAllCategories(@RequestParam("page") int page, @RequestParam("size") int size) {
        Page<Category> categories = categoryService.findAll(PageRequest.of(page, size));
        Page<CategoryDTO> categoryDTOs = categories.map(e -> modelMapper.map(e, CategoryDTO.class));
        return new ResponseEntity<Page<CategoryDTO>>(categoryDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") int id) {
        Category category = categoryService.findById(id);
        if (category==null) {
            throw new ResourceNotFoundException("Category Not Found");
        }
        return new ResponseEntity<CategoryDTO>(modelMapper.map(category, CategoryDTO.class), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryDTO categoryDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<List<String>>(ValidatorHelper.returnValidationErrors(bindingResult), HttpStatus.BAD_REQUEST);
        }
        categoryService.save(modelMapper.map(categoryDTO, Category.class));
        return new ResponseEntity<CategoryDTO>(categoryDTO, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable("id") int id, @Valid @RequestBody CategoryDTO categoryDTO,
                                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<List<String>>(ValidatorHelper.returnValidationErrors(bindingResult), HttpStatus.BAD_REQUEST);
        }
        Category category = categoryService.findById(categoryDTO.getId());
        if(category==null) {
            throw new ResourceNotFoundException("Category Not Found");
        }
        categoryService.save(modelMapper.map(categoryDTO, Category.class));
        return new ResponseEntity<CategoryDTO>(categoryDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") int id) {
        categoryService.deleteById(id);
        return ResponseEntity.ok("Delete Category Successfully!");
    }
}

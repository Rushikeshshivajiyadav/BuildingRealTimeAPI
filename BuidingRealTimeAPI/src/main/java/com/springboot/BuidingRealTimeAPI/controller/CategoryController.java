package com.springboot.BuidingRealTimeAPI.controller;

import com.springboot.BuidingRealTimeAPI.dto.CategoryDto;
import com.springboot.BuidingRealTimeAPI.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // build add Rest API
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto categoryDto){
        CategoryDto savedCategory = categoryService.addCategory(categoryDto);
        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
    }

    // Build get Category REST API

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable long categoryId){

        return new ResponseEntity<>(categoryService.getCategory(categoryId), HttpStatus.OK);
    }   

    // Build getAll categories REST API
    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories(){

        return new ResponseEntity<>( categoryService.getAllCategory(), HttpStatus.OK);
    }

    // Build Update Category REST API
    @PutMapping("/{categoryId}")
    @PreAuthorize(("hasRole('ADMIN')"))
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto, @PathVariable Long categoryId){

        return new ResponseEntity<>(categoryService.updateCategory(categoryDto,categoryId),HttpStatus.OK);
    }

    // Build delete Category Rest API
    @DeleteMapping("/{categoryId}")
    @PreAuthorize(("hasRole('ADMIN')"))
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId){
        categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>("Category deleted successfully", HttpStatus.OK);
    }
}

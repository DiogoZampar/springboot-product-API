package com.example.springboot.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.springboot.DTOs.ProductRecordDTO;
import com.example.springboot.models.Product;
import com.example.springboot.repositories.ProductRepository;

import jakarta.validation.Valid;





@RestController
public class ProductController {
    

    @Autowired //injection point, managed by Spring (inversion of control), could be done by constructor    
    ProductRepository productRepository;

    
    @PostMapping("/products")
    public ResponseEntity<Product> saveProduct(@RequestBody @Valid ProductRecordDTO productRecordDTO){
        
        var product = new Product();
        BeanUtils.copyProperties(productRecordDTO, product);
        return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(product));
        
    }



    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts(){
        //return ResponseEntity.status(HttpStatus.OK).body(productRepository.findAll());
        return ResponseEntity.ok(productRepository.findAll());
    }



    @GetMapping("/products/{id}")
    public ResponseEntity<Object> getProductByID(@PathVariable(value="id") UUID id){
        
        
        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent()){ 
            return ResponseEntity.ok(product);
        } 
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found!");

    }


    @PutMapping("/products/{id}")
    public ResponseEntity<Object> updateProductByID(@PathVariable(value="id") UUID id, @RequestBody @Valid ProductRecordDTO productRecordDTO){
        Optional<Product> product = productRepository.findById(id);     
        if(product.isPresent()){
            var productModel = product.get();
            BeanUtils.copyProperties(productRecordDTO, productModel);
            return ResponseEntity.ok(productRepository.save(productModel));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found!");


    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Object> deleteProductByID(@PathVariable(value="id") UUID id){
        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent()){
            productRepository.deleteById(id);
            return ResponseEntity.ok("Product deleted!");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found!");

    }

}



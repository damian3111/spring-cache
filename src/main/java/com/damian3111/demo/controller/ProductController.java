package com.damian3111.demo.controller;

import com.damian3111.demo.ProductDTO;
import com.damian3111.demo.entity.Product;
import com.damian3111.demo.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@RestController
public class ProductController {
    private final ProductService productService;

    @CachePut(cacheNames = "product", key = "#productDTO.id")
    @CacheEvict(cacheNames = "products", allEntries = true)
    @PostMapping("/saveProduct")
    public ResponseEntity<Product> saveProduct(@RequestBody ProductDTO productDTO){
        return ResponseEntity.ok(productService.saveProduct(productDTO));
    }

    @Cacheable(cacheNames = "product", key = "#productID")
    @GetMapping("/getProduct")
    public ResponseEntity getProduct(@RequestParam("productID") Long productID){

                try{
                    return new ResponseEntity<>(productService.getProduct(productID), HttpStatusCode.valueOf(200));
                }catch (NoSuchElementException e){
                    return new ResponseEntity<>("There is no such product!", HttpStatusCode.valueOf(400));
                }
    }

    @Cacheable(cacheNames = "products")
    @GetMapping("/getProducts")
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getProducts());
    }

    @CachePut(cacheNames = "product", key = "#productDTO.id")
    @CacheEvict(cacheNames = "products", allEntries = true)
    @PutMapping("/updateProduct")
    public ResponseEntity updateProduct(@RequestBody ProductDTO productDTO){
        try {
            return new ResponseEntity<>(productService.updateProduct(productDTO), HttpStatusCode.valueOf(200));
        }catch (NoSuchElementException e){
            return new ResponseEntity<>("There is no such product!", HttpStatusCode.valueOf(400));
        }
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = "product", key = "#id"),
            @CacheEvict(cacheNames = "products", allEntries = true)
    })
    @DeleteMapping("/deleteProduct")
    public void removeProduct(@RequestParam("productID") Long id){
        productService.removeProduct(id);
    }
}

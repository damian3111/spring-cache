package com.damian3111.demo.service;

import com.damian3111.demo.ProductDTO;
import com.damian3111.demo.entity.Product;
import com.damian3111.demo.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    public Product saveProduct(ProductDTO productDTO) {
        Product product = new Product();
        product.setPrice(productDTO.getPrice());
        product.setName(productDTO.getName());

        return productRepository.save(product);
    }

    public Product getProduct(Long productID) {
        return productRepository.findById(productID).orElseThrow();
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @Transactional
    public Product updateProduct(ProductDTO productDTO) {
        Product product = productRepository.findById(productDTO.getId()).orElseThrow();
        product.setPrice(productDTO.getPrice());
        product.setName(productDTO.getName());
        return product;
    }

    public void removeProduct(Long id) {
        productRepository.deleteById(id);
    }
}

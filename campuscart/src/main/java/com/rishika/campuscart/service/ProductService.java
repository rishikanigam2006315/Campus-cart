package com.rishika.campuscart.service;

import com.rishika.campuscart.model.Product;
import com.rishika.campuscart.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final NotificationService notificationService;

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Product addProduct(Product product){
        Product savedProduct = productRepository.save(product);
        notificationService.sendNewProductNotification(savedProduct.getTitle(), savedProduct.getPrice());

        return savedProduct;
    }
//   // public Product addProduct(Product product){
//        return productRepository.save(product);
//    }

    public Product getProductById(Long id){
        return productRepository.findById(id).orElse(null);
    }

    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }

    public Product updateProduct(Long id, Product product){

        Product existing = productRepository.findById(id).orElse(null);

        existing.setTitle(product.getTitle());
        existing.setDescription(product.getDescription());
        existing.setPrice(product.getPrice());
        existing.setLocation(product.getLocation());

        return productRepository.save(existing);
    }

    public List<Product> searchProducts(String keyword){
        return productRepository.findByTitleContainingIgnoreCase(keyword);
    }
    public List<Product> getProductsByCategory(String category){
        return productRepository.findByCategory(category);
    }

    public List<Product> getUserProducts(Long userId){
        return productRepository.findBySellerId(userId);
    }

    public List<Product> getProductsByPrice(Double minPrice, Double maxPrice){
        return productRepository.findByPriceBetween(minPrice, maxPrice);
    }
    public List<Product> getSortedProducts(String sortBy){
        return productRepository.findAll(Sort.by(sortBy));
    }
    public Page<Product> getProductsPaginated(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findAll(pageable);
    }
}
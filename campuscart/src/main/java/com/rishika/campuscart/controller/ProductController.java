package com.rishika.campuscart.controller;

import com.rishika.campuscart.model.Product;
import com.rishika.campuscart.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@CrossOrigin
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public List<Product> getProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id){
        return productService.getProductById(id);
    }

    @PostMapping
    public Product addProduct(@RequestBody Product product){
        return productService.addProduct(product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
    }

    @PutMapping("/{id}")
    public Product updateProduct(
            @PathVariable Long id,
            @RequestBody Product product){

        return productService.updateProduct(id, product);
    }

    @GetMapping("/search")
    public List<Product> searchProducts(@RequestParam String keyword){
        return productService.searchProducts(keyword);
    }

//    @PostMapping("/upload")
//    public String uploadImage(@RequestParam MultipartFile file){
//
//        return file.getOriginalFilename();
//    }
    @GetMapping("/category/{category}")
    public List<Product> getProductsByCategory(@PathVariable String category){
        return productService.getProductsByCategory(category);
    }
    @GetMapping("/user/{userId}")
    public List<Product> getUserProducts(@PathVariable Long userId){
        return productService.getUserProducts(userId);
    }
    @PostMapping("/upload")
    public String uploadImage(@RequestParam("file") MultipartFile file) throws IOException {

        String uploadDir = "uploads/";

        File directory = new File(uploadDir);
        if(!directory.exists()){
            directory.mkdirs();
        }

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        Path filePath = Paths.get(uploadDir + fileName);

        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

//        return "/uploads/" + fileName;
//    }
        return "https://campus-cart-rjnx.onrender.com/api/products/uploads/" + fileName;
    }

    // 🔥 IMAGE SERVE (MOST IMPORTANT FIX)
    @GetMapping("/uploads/{filename}")
    public Resource getImage(@PathVariable String filename) throws IOException {

        Path path = Paths.get("uploads/" + filename);
        Resource resource = new UrlResource(path.toUri());

        if (!resource.exists()) {
            throw new RuntimeException("File not found");
        }

        return resource;
    }

    @GetMapping("/price")
    public List<Product> filterByPrice(
            @RequestParam Double minPrice,
            @RequestParam Double maxPrice){

        return productService.getProductsByPrice(minPrice, maxPrice);
    }
    @GetMapping("/sorted")
    public List<Product> getSortedProducts(@RequestParam String sortBy){
        return productService.getSortedProducts(sortBy);
    }

    @GetMapping("/page")
    public Page<Product> getProductsPage(
            @RequestParam int page,
            @RequestParam int size){

        return productService.getProductsPaginated(page, size);
    }
}
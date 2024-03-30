package com.shopstream.service;

import com.shopstream.exception.ProductException;
import com.shopstream.model.Product;
import com.shopstream.request.CreateProductRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService
{
    // only for admin
    public Product createProduct(CreateProductRequest req) throws ProductException;

    public String deleteProduct(Long productId) throws ProductException;

    public Product updateProduct(Long productId,Product req)throws ProductException;

    public List<Product> getAllProducts();

    // for user and admin both
    public Product findProductById(Long id) throws ProductException;

    public List<Product> findProductByCategory(String category);

    public List<Product> searchProduct(String query);

    public Page<Product> getAllProduct(String category, List<String>colors, List<String> sizes, Integer minPrice, Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize);

    public List<Product> recentlyAddedProduct();
}

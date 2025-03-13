package com.mercheazy.server.service;

import com.mercheazy.server.dto.product.ProductRequestDto;
import com.mercheazy.server.entity.product.Product;

import java.util.List;

public interface ProductService {

    Product createProduct(ProductRequestDto productRequestDto);

    List<Product> getProducts();

    Product getProductById(int id);

    List<Product> getProductsByStoreId(int id);

    Product updateProductDetails(int id, ProductRequestDto productRequestDto);

    boolean outOfStock(int productId, int quantity);

    void updateStock(int productId, int stock);

    void deleteProduct(int id);
}

package com.mercheazy.server.service;

import com.mercheazy.server.dto.product.ProductRequestDto;
import com.mercheazy.server.entity.product.Product;
import com.mercheazy.server.entity.store.Store;
import com.mercheazy.server.entity.store.StoreOwner;
import com.mercheazy.server.entity.user.AuthUser;
import com.mercheazy.server.model.CloudinaryFile;
import com.mercheazy.server.repository.product.ProductRepository;
import com.mercheazy.server.repository.store.StoreOwnerRepository;
import com.mercheazy.server.service.impl.ProductServiceImpl;
import com.mercheazy.server.util.AuthUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private StoreOwnerRepository storeOwnerRepository;

    @Mock
    private CloudinaryService cloudinaryService;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void testCreateProduct_success() throws Exception {
        ProductRequestDto dto = new ProductRequestDto();
        dto.setName("Test Product");
        dto.setDesc("Test Description");
        dto.setListPrice(100.0);
        dto.setSellPrice(80.0);
        dto.setStock(10);
        dto.setImgFiles(List.of(mock(MultipartFile.class)));

        Store store = Store.builder().id(1).build();
        StoreOwner storeOwner = StoreOwner.builder().store(store).build();

        when(storeOwnerRepository.findByAuthUserId(anyInt()))
                .thenReturn(Optional.of(storeOwner));
        when(cloudinaryService.uploadFile(any(), anyString()))
                .thenReturn(new CloudinaryFile("imgId", "http://url"));
        when(productRepository.save(any(Product.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        try (MockedStatic<AuthUtil> mocked = Mockito.mockStatic(AuthUtil.class)) {
            when(AuthUtil.getLoggedInUser()).thenReturn(new AuthUser());

            Product res = productService.createProduct(dto);
            assertEquals("Test Product", res.getName());
            assertEquals(1, res.getProductImages().size());
        }
    }
}

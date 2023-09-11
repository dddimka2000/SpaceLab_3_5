package org.example.service;

import static org.junit.jupiter.api.Assertions.*;

import org.example.entity.CategoryEntity;
import org.example.entity.ProductEntity;
import org.example.repository.ProductRepository;
import org.example.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void testFindByName() {
        String productName = "Test Product";
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(1);

        when(productRepository.findByName(productName)).thenReturn(Optional.of(productEntity));

        Optional<ProductEntity> result = productService.findByName(productName);

        verify(productRepository, times(1)).findByName(productName);
        assertTrue(result.isPresent());
    }
    @Test
    public void testFindByIdNotFound() {
        Integer productId = 2;

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        Optional<ProductEntity> result = productService.findById(productId);

        verify(productRepository, times(1)).findById(productId);
        assertFalse(result.isPresent());
    }
    @Test
    public void testFindByNameNotFound() {
        String productName = "Nonexistent Product";

        when(productRepository.findByName(productName)).thenReturn(Optional.empty());

        Optional<ProductEntity> result = productService.findByName(productName);

        verify(productRepository, times(1)).findByName(productName);
        assertFalse(result.isPresent());
    }
    @Test
    public void testSaveProduct() {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(1);

        productService.save(productEntity);

        verify(productRepository, times(1)).save(productEntity);
    }

    @Test
    public void testFindAllProductsByCategoryEntity() {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(1);

        List<ProductEntity> productList = new ArrayList<>();
        productList.add(new ProductEntity());
        productList.add(new ProductEntity());

        when(productRepository.findByCategoryEntity(categoryEntity)).thenReturn(productList);

        List<ProductEntity> result = productService.findAllProductsByCategoryEntity(categoryEntity);

        verify(productRepository, times(1)).findByCategoryEntity(categoryEntity);
        assertEquals(2, result.size());
    }

    @Test
    public void testDeleteProduct() {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(1);

        productService.delete(productEntity);

        verify(productRepository, times(1)).delete(productEntity);
    }

    @Test
    public void testFindAllProductsPage() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<ProductEntity> productPage = mock(Page.class);

        when(productRepository.findAll(pageable)).thenReturn(productPage);

        Page<ProductEntity> result = productService.findAllProductsPage(0, 10);

        verify(productRepository, times(1)).findAll(pageable);
        assertEquals(productPage, result);
    }

    @Test
    public void testFindByNameContainingIgnoreCase() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<ProductEntity> productPage = mock(Page.class);

        when(productRepository.findByNameContainingIgnoreCase("test", pageable)).thenReturn(productPage);

        Page<ProductEntity> result = productService.findByNameContainingIgnoreCase("test", 0, 10);

        verify(productRepository, times(1)).findByNameContainingIgnoreCase("test", pageable);
        assertEquals(productPage, result);
    }

    @Test
    public void testFindByNameContainingIgnoreCaseWithStatus() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<ProductEntity> productPage = mock(Page.class);

        when(productRepository.findByNameContainingIgnoreCaseAndStatus("test", pageable, true)).thenReturn(productPage);

        Page<ProductEntity> result = productService.findByNameContainingIgnoreCaseWithStatus("test", 0, 10, true);

        verify(productRepository, times(1)).findByNameContainingIgnoreCaseAndStatus("test", pageable, true);
        assertEquals(productPage, result);
    }

    @Test
    public void testFindById() {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(1);

        when(productRepository.findById(1)).thenReturn(Optional.of(productEntity));

        Optional<ProductEntity> result = productService.findById(1);

        verify(productRepository, times(1)).findById(1);
        assertTrue(result.isPresent());
    }

    @Test
    public void testCountBy() {
        when(productRepository.countBy()).thenReturn(5L);

        long result = productService.countBy();

        verify(productRepository, times(1)).countBy();
        assertEquals(5L, result);
    }

    @Test
    public void testCountByStatusAndNameContainingIgnoreCase() {
        when(productRepository.countByStatusAndNameContainingIgnoreCase(true, "test")).thenReturn(3L);

        long result = productService.countByStatusAndNameContainingIgnoreCase(true, "test");

        verify(productRepository, times(1)).countByStatusAndNameContainingIgnoreCase(true, "test");
        assertEquals(3L, result);
    }
}

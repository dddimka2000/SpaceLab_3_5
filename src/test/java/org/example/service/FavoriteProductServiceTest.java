package org.example.service;

import static org.junit.jupiter.api.Assertions.*;

import org.example.entity.FavoriteProductEntity;
import org.example.entity.ProductEntity;
import org.example.entity.UserEntity;
import org.example.repository.FavoriteProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class FavoriteProductServiceTest {

    @Mock
    private FavoriteProductRepository favoriteProductRepository;

    @InjectMocks
    private FavoriteProductService favoriteProductService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindBestSeven() {
        // Подготавливаем мокованный результат для FavoriteProductEntity
        FavoriteProductEntity favoriteProduct1 = new FavoriteProductEntity();
        FavoriteProductEntity favoriteProduct2 = new FavoriteProductEntity();
        FavoriteProductEntity favoriteProduct3 = new FavoriteProductEntity();

        // Создаем мок ProductEntity и устанавливаем имя для него
        ProductEntity product1 = mock(ProductEntity.class);
        when(product1.getName()).thenReturn("Product 1");

        ProductEntity product2 = mock(ProductEntity.class);
        when(product2.getName()).thenReturn("Product 2");

        ProductEntity product3 = mock(ProductEntity.class);
        when(product3.getName()).thenReturn("Product 3");

        favoriteProduct1.setProductEntity(product1);
        favoriteProduct2.setProductEntity(product2);
        favoriteProduct3.setProductEntity(product3);

        List<FavoriteProductEntity> favoriteProducts = new ArrayList<>();
        favoriteProducts.add(favoriteProduct1);
        favoriteProducts.add(favoriteProduct2);
        favoriteProducts.add(favoriteProduct3);

        when(favoriteProductRepository.findAll()).thenReturn(favoriteProducts);

        Map<String, Long> result = favoriteProductService.findBestSeven();

        assertEquals(3, result.size());

        // Проверяем, что метод findAll в favoriteProductRepository был вызван один раз
        verify(favoriteProductRepository, times(1)).findAll();
    }

    @Test
    public void testFindById() {
        Integer id = 1;
        // Подготавливаем мокованный результат
        FavoriteProductEntity expectedFavoriteProduct = new FavoriteProductEntity();
        expectedFavoriteProduct.setId(id);

        when(favoriteProductRepository.findById(id)).thenReturn(Optional.of(expectedFavoriteProduct));

        Optional<FavoriteProductEntity> result = favoriteProductService.findById(id);

        assertTrue(result.isPresent());
        assertEquals(expectedFavoriteProduct, result.get());

        // Проверяем, что метод findById в favoriteProductRepository был вызван один раз с указанным аргументом
        verify(favoriteProductRepository, times(1)).findById(id);
    }

    @Test
    public void testFindByIdNotFound() {
        Integer id = 2;
        // Подготавливаем мокованный результат
        when(favoriteProductRepository.findById(id)).thenReturn(Optional.empty());

        Optional<FavoriteProductEntity> result = favoriteProductService.findById(id);

        assertFalse(result.isPresent());

        // Проверяем, что метод findById в favoriteProductRepository был вызван один раз с указанным аргументом
        verify(favoriteProductRepository, times(1)).findById(id);
    }

    @Test
    public void testDelete() {
        FavoriteProductEntity favoriteProduct = new FavoriteProductEntity();
        favoriteProduct.setId(1);

        favoriteProductService.delete(favoriteProduct);

        // Проверяем, что метод delete в favoriteProductRepository был вызван один раз с указанным аргументом
        verify(favoriteProductRepository, times(1)).delete(favoriteProduct);
    }

    @Test
    public void testSave() {
        FavoriteProductEntity favoriteProduct = new FavoriteProductEntity();
        favoriteProduct.setId(1);

        favoriteProductService.save(favoriteProduct);

        // Проверяем, что метод save в favoriteProductRepository был вызван один раз с указанным аргументом
        verify(favoriteProductRepository, times(1)).save(favoriteProduct);
    }

    @Test
    public void testFindAllFavoriteProductsByUserEntity() {
        UserEntity user = new UserEntity();
        user.setId(1);
        // Подготавливаем мокованный результат
        List<FavoriteProductEntity> expectedFavoriteProducts = new ArrayList<>();
        // Заполняем список ожидаемыми объектами
        expectedFavoriteProducts.add(new FavoriteProductEntity());
        expectedFavoriteProducts.add(new FavoriteProductEntity());

        when(favoriteProductRepository.findByUserEntity(user)).thenReturn(expectedFavoriteProducts);

        List<FavoriteProductEntity> result = favoriteProductService.findAllFavoriteProductsByUserEntity(user);

        assertEquals(expectedFavoriteProducts, result);

        // Проверяем, что метод findByUserEntity в favoriteProductRepository был вызван один раз с указанным аргументом
        verify(favoriteProductRepository, times(1)).findByUserEntity(user);
    }

    @Test
    public void testFindByUserEntityAndProductEntity() {
        UserEntity user = new UserEntity();
        user.setId(1);
        ProductEntity product = new ProductEntity();
        product.setId(2);
        // Подготавливаем мокованный результат
        FavoriteProductEntity expectedFavoriteProduct = new FavoriteProductEntity();
        expectedFavoriteProduct.setUserEntity(user);
        expectedFavoriteProduct.setProductEntity(product);

        when(favoriteProductRepository.findByUserEntityAndProductEntity(user, product)).thenReturn(Optional.of(expectedFavoriteProduct));

        Optional<FavoriteProductEntity> result = favoriteProductService.findByUserEntityAndProductEntity(user, product);

        assertTrue(result.isPresent());
        assertEquals(expectedFavoriteProduct, result.get());

        // Проверяем, что метод findByUserEntityAndProductEntity в favoriteProductRepository был вызван один раз с указанными аргументами
        verify(favoriteProductRepository, times(1)).findByUserEntityAndProductEntity(user, product);
    }

    @Test
    public void testFindByUserEntityAndProductEntityNotFound() {
        UserEntity user = new UserEntity();
        user.setId(1);
        ProductEntity product = new ProductEntity();
        product.setId(2);

        when(favoriteProductRepository.findByUserEntityAndProductEntity(user, product)).thenReturn(Optional.empty());

        Optional<FavoriteProductEntity> result = favoriteProductService.findByUserEntityAndProductEntity(user, product);

        assertFalse(result.isPresent());

        // Проверяем, что метод findByUserEntityAndProductEntity в favoriteProductRepository был вызван один раз с указанными аргументами
        verify(favoriteProductRepository, times(1)).findByUserEntityAndProductEntity(user, product);
    }
}
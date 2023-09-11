package org.example.service;

import org.example.entity.BasketItemEntity;
import org.example.entity.ProductEntity;
import org.example.entity.UserEntity;
import org.example.repository.BasketItemRepository;
import org.example.service.BasketItemService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


public class BasketItemServiceTest {

    @Mock
    private BasketItemRepository basketItemRepository;

    @InjectMocks
    private BasketItemService basketItemService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindById() {
        Integer id = 1;
        BasketItemEntity basketItemEntity = new BasketItemEntity();
        when(basketItemRepository.findById(id)).thenReturn(Optional.of(basketItemEntity));

        Optional<BasketItemEntity> foundEntity = basketItemService.findById(id);

        Assertions.assertEquals(Optional.of(basketItemEntity), foundEntity);
        verify(basketItemRepository, times(1)).findById(id);

    }

    @Test
    public void testDelete() {
        // Создаем фиктивный объект BasketItemEntity
        BasketItemEntity basketItem = new BasketItemEntity();
        basketItem.setId(1);

        // Вызываем метод delete из basketItemService
        basketItemService.delete(basketItem);

        verify(basketItemRepository, times(1)).delete(basketItem);
    }

    @Test
    public void testSave() {
        BasketItemEntity basketItem = new BasketItemEntity();
        basketItem.setId(1);

        basketItemService.save(basketItem);

        verify(basketItemRepository, times(1)).save(basketItem);
    }
    @Test
    public void testFindByIdNotFound() {
        Integer id = 2;
        // Подготавливаем мокованный результат
        when(basketItemRepository.findById(id)).thenReturn(Optional.empty());

        Optional<BasketItemEntity> result = basketItemService.findById(id);

        assertFalse(result.isPresent());

        // Проверяем, что метод findById в basketItemRepository был вызван один раз с указанным аргументом
        verify(basketItemRepository, times(1)).findById(id);
    }
    @Test
    public void testFindByUserEntityAndProductEntity() {
        UserEntity user = new UserEntity();
        ProductEntity productEntity = new ProductEntity();
        // Подготавливаем мокованный результат
        BasketItemEntity expectedBasketItem = new BasketItemEntity();
        expectedBasketItem.setId(1);

        when(basketItemRepository.findByUserEntityAndProductEntity(user, productEntity)).thenReturn(Optional.of(expectedBasketItem));

        Optional<BasketItemEntity> result = basketItemService.findByUserEntityAndProductEntity(user, productEntity);

        assertTrue(result.isPresent());
        assertEquals(expectedBasketItem, result.get());

        // Проверяем, что метод findByUserEntityAndProductEntity в basketItemRepository был вызван один раз с указанными аргументами
        verify(basketItemRepository, times(1)).findByUserEntityAndProductEntity(user, productEntity);
    }
    @Test
    public void testFindAllBasketProductsByUserEntity() {
        // Создаем фиктивного пользователя
        UserEntity user = new UserEntity();
        user.setId(1);

        // Создаем список фиктивных BasketItemEntity
        List<BasketItemEntity> basketItems = new ArrayList<>();
        BasketItemEntity item1 = new BasketItemEntity();
        BasketItemEntity item2 = new BasketItemEntity();
        basketItems.add(item1);
        basketItems.add(item2);

        when(basketItemRepository.findByUserEntity(user)).thenReturn(basketItems);

        // Вызываем метод findAllBasketProductsByUserEntity из basketItemService
        List<BasketItemEntity> result = basketItemService.findAllBasketProductsByUserEntity(user);

        // Проверяем, что результат соответствует ожидаемому списку
        assertEquals(basketItems, result);

        // Проверяем, что метод findByUserEntity в basketItemRepository был вызван один раз с ожидаемым пользователем
        verify(basketItemRepository, times(1)).findByUserEntity(user);
    }

}

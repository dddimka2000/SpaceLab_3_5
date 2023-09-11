package org.example.service;

import static org.junit.jupiter.api.Assertions.*;

import org.example.entity.OrderTableEntity;
import org.example.entity.ProductByOrderEntity;
import org.example.entity.ProductEntity;
import org.example.repository.ProductByOrderRepository;
import org.example.service.ProductByOrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class ProductByOrderServiceTest {

    @Mock
    private ProductByOrderRepository productByOrderRepository;

    @InjectMocks
    private ProductByOrderService productByOrderService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSaveProductByOrder() {
        ProductByOrderEntity productByOrder = new ProductByOrderEntity();
        productByOrder.setId(1);

        productByOrderService.save(productByOrder);

        verify(productByOrderRepository, times(1)).save(productByOrder);
    }

    @Test
    public void testFindAllByOrderTableEntity() {
        OrderTableEntity orderTableEntity = new OrderTableEntity();
        orderTableEntity.setId(1);

        List<ProductByOrderEntity> productList = new ArrayList<>();
        productList.add(new ProductByOrderEntity());
        productList.add(new ProductByOrderEntity());

        when(productByOrderRepository.findByOrderTableEntity(orderTableEntity)).thenReturn(productList);

        List<ProductByOrderEntity> result = productByOrderService.findAllByOrderTableEntity(orderTableEntity);

        verify(productByOrderRepository, times(1)).findByOrderTableEntity(orderTableEntity);
        assertEquals(2, result.size());
    }

    @Test
    public void testFindByProductEntityAndAndOrderTableEntity() {
        ProductEntity productEntity = new ProductEntity();
        OrderTableEntity orderTableEntity = new OrderTableEntity();

        when(productByOrderRepository.findByProductEntityAndAndOrderTableEntity(productEntity, orderTableEntity)).thenReturn(Optional.of(new ProductByOrderEntity()));

        Optional<ProductByOrderEntity> result = productByOrderService.findByProductEntityAndAndOrderTableEntity(productEntity, orderTableEntity);

        verify(productByOrderRepository, times(1)).findByProductEntityAndAndOrderTableEntity(productEntity, orderTableEntity);
        assertTrue(result.isPresent());
    }

    @Test
    public void testDeleteProductByOrder() {
        ProductByOrderEntity productByOrder = new ProductByOrderEntity();
        productByOrder.setId(1);

        productByOrderService.delete(productByOrder);

        verify(productByOrderRepository, times(1)).delete(productByOrder);
    }
}

package org.example.service;

import static org.junit.jupiter.api.Assertions.*;

import org.example.entity.OrderTableEntity;
import org.example.entity.UserEntity;
import org.example.repository.OrderTableRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderTableServiceTest {

    @Mock
    private OrderTableRepository orderTableRepository;

    @InjectMocks
    private OrderTableService orderTableService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindById() {
        // Создаем фиктивный объект OrderTableEntity
        OrderTableEntity expectedOrderTable = new OrderTableEntity();
        expectedOrderTable.setId(1);

        // Мокируем метод findById в orderTableRepository
        when(orderTableRepository.findById(1)).thenReturn(Optional.of(expectedOrderTable));

        // Вызываем метод findById из orderTableService
        Optional<OrderTableEntity> result = orderTableService.findById(1);

        // Проверяем, что результат соответствует ожидаемому объекту
        assertTrue(result.isPresent());
        assertEquals(expectedOrderTable, result.get());

        // Проверяем, что метод findById в orderTableRepository был вызван один раз с аргументом 1
        verify(orderTableRepository, times(1)).findById(1);
    }

    @Test
    public void testSave() {
        // Создаем фиктивный объект OrderTableEntity
        OrderTableEntity orderTableToSave = new OrderTableEntity();
        orderTableToSave.setId(1);

        // Вызываем метод save из orderTableService
        orderTableService.save(orderTableToSave);

        // Проверяем, что метод save в orderTableRepository был вызван один раз с переданным объектом
        verify(orderTableRepository, times(1)).save(orderTableToSave);
    }

    @Test
    public void testDelete() {
        // Создаем фиктивный объект OrderTableEntity
        OrderTableEntity orderTableToDelete = new OrderTableEntity();
        orderTableToDelete.setId(1);

        // Вызываем метод delete из orderTableService
        orderTableService.delete(orderTableToDelete);

        // Проверяем, что метод delete в orderTableRepository был вызван один раз с переданным объектом
        verify(orderTableRepository, times(1)).delete(orderTableToDelete);
    }

    @Test
    public void testFindAllOrdersPage() {
        // Подготавливаем мокованный результат
        List<OrderTableEntity> orderTables = new ArrayList<>();
        orderTables.add(new OrderTableEntity());
        orderTables.add(new OrderTableEntity());
        orderTables.add(new OrderTableEntity());

        // Создаем Page с фиктивными данными
        Page<OrderTableEntity> expectedPage = new PageImpl<>(orderTables);

        // Мокируем метод findAll в orderTableRepository
        when(orderTableRepository.findAll(any(Pageable.class))).thenReturn(expectedPage);

        // Вызываем метод findAllOrdersPage из orderTableService
        Page<OrderTableEntity> result = orderTableService.findAllOrdersPage(0, 3);

        // Проверяем, что результат соответствует ожидаемой странице
        assertEquals(expectedPage, result);

        // Проверяем, что метод findAll в orderTableRepository был вызван один раз с аргументами
        verify(orderTableRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    public void testFindByIdContaining() {
        // Подготавливаем мокованный результат
        List<OrderTableEntity> orderTables = new ArrayList<>();
        orderTables.add(new OrderTableEntity());
        orderTables.add(new OrderTableEntity());
        orderTables.add(new OrderTableEntity());

        // Создаем Page с фиктивными данными
        Page<OrderTableEntity> expectedPage = new PageImpl<>(orderTables);

        // Мокируем метод findById в orderTableRepository
        when(orderTableRepository.findById(anyInt(), any(Pageable.class))).thenReturn(expectedPage);

        // Вызываем метод findByIdContaining из orderTableService
        Page<OrderTableEntity> result = orderTableService.findByIdContaining("123", 0, 3);

        // Проверяем, что результат соответствует ожидаемой странице
        assertEquals(expectedPage, result);

        // Проверяем, что метод findById в orderTableRepository был вызван один раз с аргументами
        verify(orderTableRepository, times(1)).findById(anyInt(), any(Pageable.class));
    }
    @Test
    public void testCountBy() {
        // Мокируем метод countBy в orderTableRepository
        when(orderTableRepository.countBy()).thenReturn(5L);

        // Вызываем метод countBy из orderTableService
        long result = orderTableService.countBy();

        // Проверяем, что результат равен ожидаемому значению
        assertEquals(5L, result);

        // Проверяем, что метод countBy в orderTableRepository был вызван один раз
        verify(orderTableRepository, times(1)).countBy();
    }
    @Test
    public void testFindByIdContainingNumberFormatException() {
        String idOrder = "invalidId";
        Integer pageNumber = 0;
        Integer pageSize = 10;
        Page<OrderTableEntity> expectedPage = mock(Page.class);

        when(orderTableRepository.findAll(PageRequest.of(pageNumber, pageSize)))
                .thenReturn(expectedPage);

        Page<OrderTableEntity> result = orderTableService.findByIdContaining(idOrder, pageNumber, pageSize);

        verify(orderTableRepository, times(1)).findAll(PageRequest.of(pageNumber, pageSize));
    }
    @Test
    public void testFindByIdEmpty() {
        Integer id = 1;

        when(orderTableRepository.findById(id)).thenReturn(Optional.empty());

        Optional<OrderTableEntity> result = orderTableService.findById(id);

        verify(orderTableRepository, times(1)).findById(id);
    }@Test
    public void testFindAllOrderTableEntitiesByUserEntityNumberFormatException() {
        String idOrder = "invalidId";
        Integer pageNumber = 0;
        Integer pageSize = 10;
        UserEntity userEntity = new UserEntity();
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<OrderTableEntity> expectedPage = mock(Page.class);

        when(orderTableRepository.findAllByUserEntity(pageable, userEntity))
                .thenReturn(expectedPage);

        Page<OrderTableEntity> result = orderTableService.findAllOrderTableEntitiesByUserEntity(idOrder, pageNumber, pageSize, userEntity);

        verify(orderTableRepository, times(1)).findAllByUserEntity(pageable, userEntity);
    }
    @Test
    public void testFindAllOrderTableEntitiesByUserEntityWithId() {
        String idOrder = "1";
        Integer pageNumber = 0;
        Integer pageSize = 10;
        UserEntity userEntity = new UserEntity();
        int id = Integer.parseInt(idOrder);

        Page<OrderTableEntity> expectedPage = mock(Page.class);

        when(orderTableRepository.findByIdAndUserEntity(id, PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "id")), userEntity))
                .thenReturn(expectedPage);

        Page<OrderTableEntity> result = orderTableService.findAllOrderTableEntitiesByUserEntity(idOrder, pageNumber, pageSize, userEntity);

        verify(orderTableRepository, times(1)).findByIdAndUserEntity(id, PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "id")), userEntity);
    }
    // Вызываем метод countByUserEntity из orderTableService
    @Test
    public void testCountByUserEntity() {
        // Создаем фиктивного пользователя
        UserEntity user = new UserEntity();
        user.setId(1);

        // Мокируем метод countByUserEntity в orderTableRepository
        when(orderTableRepository.countByUserEntity(user)).thenReturn(3L);
    long result = orderTableService.countByUserEntity(user);

    // Проверяем, что результат равен ожидаемому значению
    assertEquals(3L, result);

    // Проверяем, что метод countByUserEntity в orderTableRepository был вызван один раз с переданным пользователем
    verify(orderTableRepository, times(1)).countByUserEntity(user);
}

}


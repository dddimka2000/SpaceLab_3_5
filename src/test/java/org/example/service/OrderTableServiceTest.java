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
        // ������� ��������� ������ OrderTableEntity
        OrderTableEntity expectedOrderTable = new OrderTableEntity();
        expectedOrderTable.setId(1);

        // �������� ����� findById � orderTableRepository
        when(orderTableRepository.findById(1)).thenReturn(Optional.of(expectedOrderTable));

        // �������� ����� findById �� orderTableService
        Optional<OrderTableEntity> result = orderTableService.findById(1);

        // ���������, ��� ��������� ������������� ���������� �������
        assertTrue(result.isPresent());
        assertEquals(expectedOrderTable, result.get());

        // ���������, ��� ����� findById � orderTableRepository ��� ������ ���� ��� � ���������� 1
        verify(orderTableRepository, times(1)).findById(1);
    }

    @Test
    public void testSave() {
        // ������� ��������� ������ OrderTableEntity
        OrderTableEntity orderTableToSave = new OrderTableEntity();
        orderTableToSave.setId(1);

        // �������� ����� save �� orderTableService
        orderTableService.save(orderTableToSave);

        // ���������, ��� ����� save � orderTableRepository ��� ������ ���� ��� � ���������� ��������
        verify(orderTableRepository, times(1)).save(orderTableToSave);
    }

    @Test
    public void testDelete() {
        // ������� ��������� ������ OrderTableEntity
        OrderTableEntity orderTableToDelete = new OrderTableEntity();
        orderTableToDelete.setId(1);

        // �������� ����� delete �� orderTableService
        orderTableService.delete(orderTableToDelete);

        // ���������, ��� ����� delete � orderTableRepository ��� ������ ���� ��� � ���������� ��������
        verify(orderTableRepository, times(1)).delete(orderTableToDelete);
    }

    @Test
    public void testFindAllOrdersPage() {
        // �������������� ���������� ���������
        List<OrderTableEntity> orderTables = new ArrayList<>();
        orderTables.add(new OrderTableEntity());
        orderTables.add(new OrderTableEntity());
        orderTables.add(new OrderTableEntity());

        // ������� Page � ���������� �������
        Page<OrderTableEntity> expectedPage = new PageImpl<>(orderTables);

        // �������� ����� findAll � orderTableRepository
        when(orderTableRepository.findAll(any(Pageable.class))).thenReturn(expectedPage);

        // �������� ����� findAllOrdersPage �� orderTableService
        Page<OrderTableEntity> result = orderTableService.findAllOrdersPage(0, 3);

        // ���������, ��� ��������� ������������� ��������� ��������
        assertEquals(expectedPage, result);

        // ���������, ��� ����� findAll � orderTableRepository ��� ������ ���� ��� � �����������
        verify(orderTableRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    public void testFindByIdContaining() {
        // �������������� ���������� ���������
        List<OrderTableEntity> orderTables = new ArrayList<>();
        orderTables.add(new OrderTableEntity());
        orderTables.add(new OrderTableEntity());
        orderTables.add(new OrderTableEntity());

        // ������� Page � ���������� �������
        Page<OrderTableEntity> expectedPage = new PageImpl<>(orderTables);

        // �������� ����� findById � orderTableRepository
        when(orderTableRepository.findById(anyInt(), any(Pageable.class))).thenReturn(expectedPage);

        // �������� ����� findByIdContaining �� orderTableService
        Page<OrderTableEntity> result = orderTableService.findByIdContaining("123", 0, 3);

        // ���������, ��� ��������� ������������� ��������� ��������
        assertEquals(expectedPage, result);

        // ���������, ��� ����� findById � orderTableRepository ��� ������ ���� ��� � �����������
        verify(orderTableRepository, times(1)).findById(anyInt(), any(Pageable.class));
    }
    @Test
    public void testCountBy() {
        // �������� ����� countBy � orderTableRepository
        when(orderTableRepository.countBy()).thenReturn(5L);

        // �������� ����� countBy �� orderTableService
        long result = orderTableService.countBy();

        // ���������, ��� ��������� ����� ���������� ��������
        assertEquals(5L, result);

        // ���������, ��� ����� countBy � orderTableRepository ��� ������ ���� ���
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
    // �������� ����� countByUserEntity �� orderTableService
    @Test
    public void testCountByUserEntity() {
        // ������� ���������� ������������
        UserEntity user = new UserEntity();
        user.setId(1);

        // �������� ����� countByUserEntity � orderTableRepository
        when(orderTableRepository.countByUserEntity(user)).thenReturn(3L);
    long result = orderTableService.countByUserEntity(user);

    // ���������, ��� ��������� ����� ���������� ��������
    assertEquals(3L, result);

    // ���������, ��� ����� countByUserEntity � orderTableRepository ��� ������ ���� ��� � ���������� �������������
    verify(orderTableRepository, times(1)).countByUserEntity(user);
}

}


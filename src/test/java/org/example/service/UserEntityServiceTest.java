package org.example.service;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.apache.logging.log4j.Logger;
import org.example.entity.UserEntity;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import java.util.*;

public class UserEntityServiceTest {

    private UserRepository userRepository;
    private UserEntityService userEntityService;

    @BeforeEach
    public void setUp() {
        userRepository = mock(UserRepository.class);
        userEntityService = new UserEntityService(userRepository);
    }

    @Test
    public void testFindByEmail() {
        String email = "test@example.com";
        UserEntity userEntity = new UserEntity();
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(userEntity));

        Optional<UserEntity> result = userEntityService.findByEmail(email);

        assertTrue(result.isPresent());
        assertEquals(userEntity, result.get());
    }

    @Test
    public void testFindByTelephone() {
        String telephone = "+1234567890";
        UserEntity userEntity = new UserEntity();
        when(userRepository.findByTelephone(telephone)).thenReturn(Optional.of(userEntity));

        Optional<UserEntity> result = userEntityService.findByTelephone(telephone);

        assertTrue(result.isPresent());
        assertEquals(userEntity, result.get());
    }

    @Test
    public void testFindByLogin() {
        String login = "testuser";
        UserEntity userEntity = new UserEntity();
        when(userRepository.findByLogin(login)).thenReturn(Optional.of(userEntity));

        Optional<UserEntity> result = userEntityService.findByLogin(login);

        assertTrue(result.isPresent());
        assertEquals(userEntity, result.get());
    }

    @Test
    public void testFindById() {
        Integer userId = 1;
        UserEntity userEntity = new UserEntity();
        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));

        Optional<UserEntity> result = userEntityService.findById(userId);

        assertTrue(result.isPresent());
        assertEquals(userEntity, result.get());
    }

    @Test
    public void testSave() {
        UserEntity userEntity = new UserEntity();
        userEntityService.save(userEntity);

        verify(userRepository, times(1)).save(userEntity);
    }

    @Test
    public void testDelete() {
        UserEntity userEntity = new UserEntity();
        userEntityService.delete(userEntity);

        verify(userRepository, times(1)).delete(userEntity);
    }

    @Test
    public void testFindAllUsers() {
        List<UserEntity> userList = Arrays.asList(new UserEntity(), new UserEntity());
        when(userRepository.findAll()).thenReturn(userList);

        List<UserEntity> result = userEntityService.findAllUsers();

        assertEquals(userList, result);
    }

    @Test
    public void testFindAllUsersPage() {
        int pageNumber = 0;
        int pageSize = 10;
        List<UserEntity> userList = Arrays.asList(new UserEntity(), new UserEntity());
        Page<UserEntity> userPage = new PageImpl<>(userList);
        when(userRepository.findAll(any(Pageable.class))).thenReturn(userPage);

        Page<UserEntity> result = userEntityService.findAllUsersPage(pageNumber, pageSize);

        assertEquals(userPage, result);
    }

    @Test
    public void testFindPageAllUsersBySearchNameWithSort() {
        String searchName = "search";
        String sortName = "id,asc";
        Integer pageNumber = 0;
        Integer pageSize = 10;

        List<UserEntity> mockUserEntities = new ArrayList<>();
        // Здесь добавьте несколько фиктивных объектов UserEntity в mockUserEntities

        Page<UserEntity> mockPage = new PageImpl<>(mockUserEntities);

        when(userRepository.findByLoginContainingIgnoreCase(eq(searchName), any(Pageable.class))).thenReturn(mockPage);

        Page<UserEntity> resultPage = userEntityService.findPageAllUsersBySearchName(searchName, sortName, pageNumber, pageSize);

        assertEquals(mockPage, resultPage);
        verify(userRepository, times(1)).findByLoginContainingIgnoreCase(eq(searchName), any(Pageable.class));
    }

    @Test
    public void testFindAllUsersEmptyList() {
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        List<UserEntity> result = userEntityService.findAllUsers();

        assertTrue(result.isEmpty());

    }

    @Test
    public void testFindAllUsersNonEmptyList() {
        List<UserEntity> userList = Arrays.asList(new UserEntity(), new UserEntity());
        when(userRepository.findAll()).thenReturn(userList);

        List<UserEntity> result = userEntityService.findAllUsers();

        assertFalse(result.isEmpty());

    }
    @Test
    public void testCountBy() {
        long count = 5;
        when(userRepository.countBy()).thenReturn(count);

        long result = userEntityService.countBy();

        assertEquals(count, result);
    }

    @Test
    public void testCountByLogin() {
        String login = "testuser";
        long count = 3;
        when(userRepository.countByLoginContainingIgnoreCase(login)).thenReturn(count);

        long result = userEntityService.countByLogin(login);

        assertEquals(count, result);
    }

    @Test
    public void testFindPageAllUsersBySearchNameWithSortByIdAsc() {
        String searchName = "search";
        String sortName = "id,asc";
        Integer pageNumber = 0;
        Integer pageSize = 10;

        List<UserEntity> mockUserEntities = new ArrayList<>();
        // Добавьте несколько фиктивных объектов UserEntity в mockUserEntities

        Page<UserEntity> mockPage = new PageImpl<>(mockUserEntities);

        when(userRepository.findByLoginContainingIgnoreCase(eq(searchName), any(Pageable.class))).thenReturn(mockPage);

        Page<UserEntity> resultPage = userEntityService.findPageAllUsersBySearchName(searchName, sortName, pageNumber, pageSize);

        assertEquals(mockPage, resultPage);
        verify(userRepository, times(1)).findByLoginContainingIgnoreCase(eq(searchName), any(Pageable.class));
    }

    @Test
    public void testFindPageAllUsersBySearchNameWithSortByUsernameDesc() {
        String searchName = "search";
        String sortName = "username,desc";
        Integer pageNumber = 0;
        Integer pageSize = 10;

        List<UserEntity> mockUserEntities = new ArrayList<>();
        // Добавьте несколько фиктивных объектов UserEntity в mockUserEntities

        Page<UserEntity> mockPage = new PageImpl<>(mockUserEntities);

        when(userRepository.findByLoginContainingIgnoreCase(eq(searchName), any(Pageable.class))).thenReturn(mockPage);

        Page<UserEntity> resultPage = userEntityService.findPageAllUsersBySearchName(searchName, sortName, pageNumber, pageSize);

        assertEquals(mockPage, resultPage);
        verify(userRepository, times(1)).findByLoginContainingIgnoreCase(eq(searchName), any(Pageable.class));
    }

    @Test
    public void testFindPageAllUsersBySearchNameWithoutSort() {
        String searchName = "search";
        String sortName = null; // Без сортировки
        Integer pageNumber = 0;
        Integer pageSize = 10;

        List<UserEntity> mockUserEntities = new ArrayList<>();
        // Добавьте несколько фиктивных объектов UserEntity в mockUserEntities

        Page<UserEntity> mockPage = new PageImpl<>(mockUserEntities);

        // Настройка мока userRepository для возврата mockPage при вызове findByLoginContainingIgnoreCase
        when(userRepository.findByLoginContainingIgnoreCase(eq(searchName), any(Pageable.class))).thenReturn(mockPage);

        Page<UserEntity> resultPage = userEntityService.findPageAllUsersBySearchName(searchName, sortName, pageNumber, pageSize);

        assertEquals(mockPage, resultPage);
        verify(userRepository, times(1)).findByLoginContainingIgnoreCase(eq(searchName), any(Pageable.class));
    }

    @Test
    public void testFindPageAllUsersBySearchNameWithSortByEmailAsc() {
        String searchName = "search";
        String sortName = "email,asc";
        Integer pageNumber = 0;
        Integer pageSize = 10;

        List<UserEntity> mockUserEntities = new ArrayList<>();
        // Добавьте несколько фиктивных объектов UserEntity в mockUserEntities

        Page<UserEntity> mockPage = new PageImpl<>(mockUserEntities);

        when(userRepository.findByLoginContainingIgnoreCase(eq(searchName), any(Pageable.class))).thenReturn(mockPage);

        Page<UserEntity> resultPage = userEntityService.findPageAllUsersBySearchName(searchName, sortName, pageNumber, pageSize);

        assertEquals(mockPage, resultPage);
        verify(userRepository, times(1)).findByLoginContainingIgnoreCase(eq(searchName), any(Pageable.class));
    }

    @Test
    public void testFindPageAllUsersBySearchNameWithSortByLoginAsc() {
        String searchName = "search";
        String sortName = "login,asc";
        Integer pageNumber = 0;
        Integer pageSize = 10;

        List<UserEntity> mockUserEntities = new ArrayList<>();
        // Добавьте несколько фиктивных объектов UserEntity в mockUserEntities

        Page<UserEntity> mockPage = new PageImpl<>(mockUserEntities);

        when(userRepository.findByLoginContainingIgnoreCase(eq(searchName), any(Pageable.class))).thenReturn(mockPage);

        Page<UserEntity> resultPage = userEntityService.findPageAllUsersBySearchName(searchName, sortName, pageNumber, pageSize);

        assertEquals(mockPage, resultPage);
        verify(userRepository, times(1)).findByLoginContainingIgnoreCase(eq(searchName), any(Pageable.class));
    }






    @Test
    public void testFindPageAllUsersBySearchNameWithInvalidSort() {
        String searchName = "search";
        String sortName = "invalidSort"; // Неверная сортировка
        Integer pageNumber = 0;
        Integer pageSize = 10;

        List<UserEntity> mockUserEntities = new ArrayList<>();
        // Здесь добавьте несколько фиктивных объектов UserEntity в mockUserEntities

        Page<UserEntity> mockPage = new PageImpl<>(mockUserEntities);

        when(userRepository.findByLoginContainingIgnoreCase(eq(searchName), any(Pageable.class))).thenReturn(mockPage);

        Page<UserEntity> resultPage = userEntityService.findPageAllUsersBySearchName(searchName, sortName, pageNumber, pageSize);

        assertEquals(mockPage, resultPage);
        verify(userRepository, times(1)).findByLoginContainingIgnoreCase(eq(searchName), any(Pageable.class));
    }
    @Test
    public void testFindPageAllUsersBySearchNameWithEmptySearchName() {
        String searchName = ""; // Пустой поисковый запрос
        String sortName = "id,asc";
        Integer pageNumber = 0;
        Integer pageSize = 10;

        List<UserEntity> mockUserEntities = new ArrayList<>();
        // Добавьте несколько фиктивных объектов UserEntity в mockUserEntities

        Page<UserEntity> mockPage = new PageImpl<>(mockUserEntities);

        when(userRepository.findAll(any(Pageable.class))).thenReturn(mockPage);

        Page<UserEntity> resultPage = userEntityService.findPageAllUsersBySearchName(searchName, sortName, pageNumber, pageSize);

        assertEquals(mockPage, resultPage);
        verify(userRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    public void testFindPageAllUsersBySearchNameWithEmptyResult() {
        String searchName = "nonExistentName"; // Поиск, который не возвращает результатов
        String sortName = "id,asc";
        Integer pageNumber = 0;
        Integer pageSize = 10;

        List<UserEntity> mockUserEntities = new ArrayList<>();

        Page<UserEntity> mockPage = new PageImpl<>(mockUserEntities);

        when(userRepository.findByLoginContainingIgnoreCase(eq(searchName), any(Pageable.class))).thenReturn(mockPage);

        Page<UserEntity> resultPage = userEntityService.findPageAllUsersBySearchName(searchName, sortName, pageNumber, pageSize);

        assertEquals(mockPage, resultPage);
        verify(userRepository, times(1)).findByLoginContainingIgnoreCase(eq(searchName), any(Pageable.class));
    }

    @Test
    public void testFindPageAllUsersBySearchNameWithNullSearchName() {
        String searchName = null; // Поиск с null значением
        String sortName = "id,asc";
        Integer pageNumber = 0;
        Integer pageSize = 10;

        List<UserEntity> mockUserEntities = new ArrayList<>();
        // Добавьте несколько фиктивных объектов UserEntity в mockUserEntities

        Page<UserEntity> mockPage = new PageImpl<>(mockUserEntities);

        when(userRepository.findAll(any(Pageable.class))).thenReturn(mockPage);

        Page<UserEntity> resultPage = userEntityService.findPageAllUsersBySearchName(searchName, sortName, pageNumber, pageSize);

        assertEquals(mockPage, resultPage);
        verify(userRepository, times(1)).findAll(any(Pageable.class));
    }
    @Test
    public void testFindPageAllUsersBySearchNameWithEmptySearchNameTest() {
        String searchName = ""; // Поиск с null значением
        String sortName = "id,asc";
        Integer pageNumber = 0;
        Integer pageSize = 10;

        List<UserEntity> mockUserEntities = new ArrayList<>();
        // Добавьте несколько фиктивных объектов UserEntity в mockUserEntities

        Page<UserEntity> mockPage = new PageImpl<>(mockUserEntities);

        when(userRepository.findAll(any(Pageable.class))).thenReturn(mockPage);

        Page<UserEntity> resultPage = userEntityService.findPageAllUsersBySearchName(searchName, sortName, pageNumber, pageSize);

        assertEquals(mockPage, resultPage);
        verify(userRepository, times(1)).findAll(any(Pageable.class));
    }
    @Test
    public void testFindPageAllUsersBySearchNameWithSortByUsernameAsc() {
        String searchName = "search";
        String sortName = "username,asc"; // Сортировка по "username" в порядке возрастания
        Integer pageNumber = 0;
        Integer pageSize = 10;

        List<UserEntity> mockUserEntities = new ArrayList<>();
        // Добавьте несколько фиктивных объектов UserEntity в mockUserEntities

        Page<UserEntity> mockPage = new PageImpl<>(mockUserEntities);

        // Настройка мока userRepository для возврата mockPage при вызове findByLoginContainingIgnoreCase
        when(userRepository.findByLoginContainingIgnoreCase(eq(searchName), any(Pageable.class))).thenReturn(mockPage);

        Page<UserEntity> resultPage = userEntityService.findPageAllUsersBySearchName(searchName, sortName, pageNumber, pageSize);

        assertEquals(mockPage, resultPage);
        verify(userRepository, times(1)).findByLoginContainingIgnoreCase(eq(searchName), any(Pageable.class));
    }

}

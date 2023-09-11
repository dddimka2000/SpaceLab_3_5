package org.example.service;

import static org.junit.jupiter.api.Assertions.*;

import org.example.entity.CategoryEntity;
import org.example.entity.ClassificationEntity;
import org.example.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindById() {
        Integer id = 1;
        // Подготавливаем мокованный результат
        CategoryEntity expectedCategory = new CategoryEntity();
        expectedCategory.setId(id);

        when(categoryRepository.findById(id)).thenReturn(Optional.of(expectedCategory));

        Optional<CategoryEntity> result = categoryService.findById(id);

        assertTrue(result.isPresent());
        assertEquals(expectedCategory, result.get());

        // Проверяем, что метод findById в categoryRepository был вызван один раз с указанным аргументом
        verify(categoryRepository, times(1)).findById(id);
    }

    @Test
    public void testFindByIdNotFound() {
        Integer id = 2;
        // Подготавливаем мокованный результат
        when(categoryRepository.findById(id)).thenReturn(Optional.empty());

        Optional<CategoryEntity> result = categoryService.findById(id);

        assertFalse(result.isPresent());

        // Проверяем, что метод findById в categoryRepository был вызван один раз с указанным аргументом
        verify(categoryRepository, times(1)).findById(id);
    }

    @Test
    public void testSave() {
        CategoryEntity category = new CategoryEntity();
        category.setId(1);

        categoryService.save(category);

        // Проверяем, что метод save в categoryRepository был вызван один раз с указанным аргументом
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    public void testFindAllCategoriesByClassificationEntity() {
        ClassificationEntity classificationEntity = new ClassificationEntity();
        classificationEntity.setId(1);

        // Подготавливаем мокованный результат
        List<CategoryEntity> expectedCategories = new ArrayList<>();
        // Заполняем список ожидаемыми объектами
        expectedCategories.add(new CategoryEntity());
        expectedCategories.add(new CategoryEntity());

        when(categoryRepository.findByClassificationEntity(classificationEntity)).thenReturn(expectedCategories);

        List<CategoryEntity> result = categoryService.findAllCategoriesByClassificationEntity(classificationEntity);

        assertEquals(expectedCategories, result);

        // Проверяем, что метод findByClassificationEntity в categoryRepository был вызван один раз с указанным аргументом
        verify(categoryRepository, times(1)).findByClassificationEntity(classificationEntity);
    }

    @Test
    public void testFindByName() {
        String name = "CategoryName";
        // Подготавливаем мокованный результат
        CategoryEntity expectedCategory = new CategoryEntity();
        expectedCategory.setName(name);

        when(categoryRepository.findByName(name)).thenReturn(Optional.of(expectedCategory));

        Optional<CategoryEntity> result = categoryService.findByName(name);

        assertTrue(result.isPresent());
        assertEquals(expectedCategory, result.get());

        // Проверяем, что метод findByName в categoryRepository был вызван один раз с указанным аргументом
        verify(categoryRepository, times(1)).findByName(name);
    }

    @Test
    public void testFindByNameNotFound() {
        String name = "NonExistentCategory";
        // Подготавливаем мокованный результат
        when(categoryRepository.findByName(name)).thenReturn(Optional.empty());

        Optional<CategoryEntity> result = categoryService.findByName(name);

        assertFalse(result.isPresent());

        // Проверяем, что метод findByName в categoryRepository был вызван один раз с указанным аргументом
        verify(categoryRepository, times(1)).findByName(name);
    }

    @Test
    public void testDelete() {
        CategoryEntity category = new CategoryEntity();
        category.setId(1);

        categoryService.delete(category);

        // Проверяем, что метод delete в categoryRepository был вызван один раз с указанным аргументом
        verify(categoryRepository, times(1)).delete(category);
    }

    // Добавьте другие тесты здесь...

}
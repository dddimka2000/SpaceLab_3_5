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
        // �������������� ���������� ���������
        CategoryEntity expectedCategory = new CategoryEntity();
        expectedCategory.setId(id);

        when(categoryRepository.findById(id)).thenReturn(Optional.of(expectedCategory));

        Optional<CategoryEntity> result = categoryService.findById(id);

        assertTrue(result.isPresent());
        assertEquals(expectedCategory, result.get());

        // ���������, ��� ����� findById � categoryRepository ��� ������ ���� ��� � ��������� ����������
        verify(categoryRepository, times(1)).findById(id);
    }

    @Test
    public void testFindByIdNotFound() {
        Integer id = 2;
        // �������������� ���������� ���������
        when(categoryRepository.findById(id)).thenReturn(Optional.empty());

        Optional<CategoryEntity> result = categoryService.findById(id);

        assertFalse(result.isPresent());

        // ���������, ��� ����� findById � categoryRepository ��� ������ ���� ��� � ��������� ����������
        verify(categoryRepository, times(1)).findById(id);
    }

    @Test
    public void testSave() {
        CategoryEntity category = new CategoryEntity();
        category.setId(1);

        categoryService.save(category);

        // ���������, ��� ����� save � categoryRepository ��� ������ ���� ��� � ��������� ����������
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    public void testFindAllCategoriesByClassificationEntity() {
        ClassificationEntity classificationEntity = new ClassificationEntity();
        classificationEntity.setId(1);

        // �������������� ���������� ���������
        List<CategoryEntity> expectedCategories = new ArrayList<>();
        // ��������� ������ ���������� ���������
        expectedCategories.add(new CategoryEntity());
        expectedCategories.add(new CategoryEntity());

        when(categoryRepository.findByClassificationEntity(classificationEntity)).thenReturn(expectedCategories);

        List<CategoryEntity> result = categoryService.findAllCategoriesByClassificationEntity(classificationEntity);

        assertEquals(expectedCategories, result);

        // ���������, ��� ����� findByClassificationEntity � categoryRepository ��� ������ ���� ��� � ��������� ����������
        verify(categoryRepository, times(1)).findByClassificationEntity(classificationEntity);
    }

    @Test
    public void testFindByName() {
        String name = "CategoryName";
        // �������������� ���������� ���������
        CategoryEntity expectedCategory = new CategoryEntity();
        expectedCategory.setName(name);

        when(categoryRepository.findByName(name)).thenReturn(Optional.of(expectedCategory));

        Optional<CategoryEntity> result = categoryService.findByName(name);

        assertTrue(result.isPresent());
        assertEquals(expectedCategory, result.get());

        // ���������, ��� ����� findByName � categoryRepository ��� ������ ���� ��� � ��������� ����������
        verify(categoryRepository, times(1)).findByName(name);
    }

    @Test
    public void testFindByNameNotFound() {
        String name = "NonExistentCategory";
        // �������������� ���������� ���������
        when(categoryRepository.findByName(name)).thenReturn(Optional.empty());

        Optional<CategoryEntity> result = categoryService.findByName(name);

        assertFalse(result.isPresent());

        // ���������, ��� ����� findByName � categoryRepository ��� ������ ���� ��� � ��������� ����������
        verify(categoryRepository, times(1)).findByName(name);
    }

    @Test
    public void testDelete() {
        CategoryEntity category = new CategoryEntity();
        category.setId(1);

        categoryService.delete(category);

        // ���������, ��� ����� delete � categoryRepository ��� ������ ���� ��� � ��������� ����������
        verify(categoryRepository, times(1)).delete(category);
    }

    // �������� ������ ����� �����...

}
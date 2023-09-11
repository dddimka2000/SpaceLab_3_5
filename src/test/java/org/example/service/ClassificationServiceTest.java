package org.example.service;

import static org.junit.jupiter.api.Assertions.*;

import org.example.entity.ClassificationEntity;
import org.example.repository.ClassificationRepository;
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

public class ClassificationServiceTest {

    @Mock
    private ClassificationRepository classificationRepository;

    @InjectMocks
    private ClassificationService classificationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindByName() {
        String name = "ClassificationName";
        // �������������� ���������� ���������
        ClassificationEntity expectedClassification = new ClassificationEntity();
        expectedClassification.setName(name);

        when(classificationRepository.findByName(name)).thenReturn(Optional.of(expectedClassification));

        Optional<ClassificationEntity> result = classificationService.findByName(name);

        assertTrue(result.isPresent());
        assertEquals(expectedClassification, result.get());

        // ���������, ��� ����� findByName � classificationRepository ��� ������ ���� ��� � ��������� ����������
        verify(classificationRepository, times(1)).findByName(name);
    }

    @Test
    public void testFindByNameNotFound() {
        String name = "NonExistentClassification";
        // �������������� ���������� ���������
        when(classificationRepository.findByName(name)).thenReturn(Optional.empty());

        Optional<ClassificationEntity> result = classificationService.findByName(name);

        assertFalse(result.isPresent());

        // ���������, ��� ����� findByName � classificationRepository ��� ������ ���� ��� � ��������� ����������
        verify(classificationRepository, times(1)).findByName(name);
    }

    @Test
    public void testSave() {
        ClassificationEntity classification = new ClassificationEntity();
        classification.setId(1);

        classificationService.save(classification);

        // ���������, ��� ����� save � classificationRepository ��� ������ ���� ��� � ��������� ����������
        verify(classificationRepository, times(1)).save(classification);
    }

    @Test
    public void testFindAllClassificationEntities() {
        // �������������� ���������� ���������
        List<ClassificationEntity> expectedClassifications = new ArrayList<>();
        // ��������� ������ ���������� ���������
        expectedClassifications.add(new ClassificationEntity());
        expectedClassifications.add(new ClassificationEntity());

        when(classificationRepository.findAll()).thenReturn(expectedClassifications);

        List<ClassificationEntity> result = classificationService.findAllClassificationEntities();

        assertEquals(expectedClassifications, result);

        // ���������, ��� ����� findAll � classificationRepository ��� ������ ���� ���
        verify(classificationRepository, times(1)).findAll();
    }

    @Test
    public void testDelete() {
        ClassificationEntity classification = new ClassificationEntity();
        classification.setId(1);

        classificationService.delete(classification);

        // ���������, ��� ����� delete � classificationRepository ��� ������ ���� ��� � ��������� ����������
        verify(classificationRepository, times(1)).delete(classification);
    }

    // �������� ������ ����� �����...

}

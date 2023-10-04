package org.example.repository;

import org.example.entity.ClassificationEntity;
import org.example.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassificationRepository extends JpaRepository<ClassificationEntity, Integer> {
    Optional<ClassificationEntity> findByName(String name);

    @Override
    <S extends ClassificationEntity> S save(S entity);

    @Override
    List<ClassificationEntity> findAll();

    Page<ClassificationEntity> findByNameContainingIgnoreCase(String name, Pageable pageable);

    @Override
    long count();
}

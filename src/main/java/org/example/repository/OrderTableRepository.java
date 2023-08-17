package org.example.repository;

import org.example.entity.OrderTableEntity;
import org.example.entity.ProductEntity;
import org.example.entity.UserEntity;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderTableRepository extends JpaRepository< OrderTableEntity, Integer> {

    @Override
    OrderTableEntity getById(Integer integer);

    @Override
    <S extends OrderTableEntity> S save(S entity);

    @Override
    void delete(OrderTableEntity entity);

    Optional<OrderTableEntity> findById(Integer integer);


    Page<OrderTableEntity> findAll(Pageable pageable);
    List<OrderTableEntity> findAllByUserEntity(UserEntity user);


    Page<OrderTableEntity> findById(Integer integer, Pageable pageable);

    long countBy();
}

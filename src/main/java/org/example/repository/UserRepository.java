package org.example.repository;

import org.example.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Integer> {
    Optional<UserEntity> findByLogin(String login);

    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByTelephone(String telephone);

    @Override
    <S extends UserEntity> S save(S entity);

    @Override
    Page<UserEntity> findAll(Pageable pageable);
}

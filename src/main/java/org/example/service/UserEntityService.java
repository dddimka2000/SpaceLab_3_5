package org.example.service;

import lombok.extern.log4j.Log4j2;
import org.example.entity.UserEntity;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
@Service
public class UserEntityService {
    final
    UserRepository userRepository;

    public UserEntityService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<UserEntity> findByEmail(String email) {
        log.info("UserEntity-findByEmail start");
        Optional<UserEntity> user;
        user = userRepository.findByEmail(email);
        log.info("UserEntity-findByEmail successful");

        return user;
    }

    public Optional<UserEntity> findByTelephone(String telephone) {
        log.info("UserEntity-findByTelephone start");
        Optional<UserEntity> user;
        user = userRepository.findByTelephone(telephone);
        log.info("UserEntity-findByTelephone successful");

        return user;
    }

    public Optional<UserEntity> findByLogin(String login) {
        log.info("UserEntity-findByLogin start");
        Optional<UserEntity> user = userRepository.findByLogin(login);
        log.info("UserEntity-findByLogin successful");

        return user;
    }

    public void save(UserEntity userEntity) {
        log.info("UserEntity-saveByLogin start");
        userRepository.save(userEntity);
        log.info("UserEntity-findByLogin successful");

    }

    public Page<UserEntity> findAllUsersPage(Integer pageNumber, Integer pageSize) {
        log.info("UserEntity-findAllUsersPage start");
        Page<UserEntity> page = null;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        page = userRepository.findAll(pageable);
        log.info("Users with " + pageNumber + " has been found");
        log.info("UserEntity-findAllUsersPage successfully");
        return page;

    }
}
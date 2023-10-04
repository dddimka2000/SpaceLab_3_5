package org.example.service;

import lombok.extern.log4j.Log4j2;
import org.example.entity.ClassificationEntity;
import org.example.entity.OrderTableEntity;
import org.example.entity.UserEntity;
import org.example.repository.ClassificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class ClassificationService {
    private final
    ClassificationRepository classificationRepository;

    public ClassificationService(ClassificationRepository classificationRepository) {
        this.classificationRepository = classificationRepository;
    }

    public Optional<ClassificationEntity> findByName(String login) {
        log.info("ClassificationService-findByName start: " + login);
        Optional<ClassificationEntity> classificationEntity = classificationRepository.findByName(login);
        if (classificationEntity.isPresent()) {
            log.info("ClassificationService-findByName successful: " + classificationEntity);
        } else {
            log.info("classificationEntity empty");
        }
        return classificationEntity;
    }

    public void save(ClassificationEntity classificationEntity) {
        log.info("ClassificationEntity-save start: " + classificationEntity.getId());
        classificationRepository.save(classificationEntity);
        log.info("ClassificationEntity-save successful");
    }

    public List<ClassificationEntity> findAllClassificationEntities() {
        log.info("ClassificationService-findAllClassificationEntities");
        List<ClassificationEntity> classificationEntities = classificationRepository.findAll();
        log.info("ClassificationService-findAllClassificationEntities successful");
        return classificationEntities;
    }
    public Page<ClassificationEntity> findByNameContainingIgnoreCase (String name, Integer pageNumber, Integer pageSize)  {
        log.info("ClassificationService-findByNameContainingIgnoreCase");
        Page<ClassificationEntity> page = null;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        page = classificationRepository.findByNameContainingIgnoreCase(name,pageable);
        log.info("ClassificationService-findByNameContainingIgnoreCase successful");
        return page;
    }

    public long countBy() {
        log.info("OrderTableService-countBy");
        long count = classificationRepository.count();
        log.info("OrderTableService-countBy get: " + count);
        return count;
    }
    public void delete(ClassificationEntity classificationEntity) {
        log.info("ClassificationEntity-delete start: " + classificationEntity.getId());
        classificationRepository.delete(classificationEntity);
        log.info("ClassificationEntity-delete successful");
    }



}

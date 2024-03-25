package org.example.adminservice.functionality.repository;

import org.example.adminservice.functionality.model.Functionality;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FunctionalityRepository extends MongoRepository<Functionality,String> {
    List<Functionality> findAllByOperatorId(String operatorId);
}

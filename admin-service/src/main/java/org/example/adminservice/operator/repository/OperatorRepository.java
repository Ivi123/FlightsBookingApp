package org.example.adminservice.operator.repository;

import org.example.adminservice.operator.model.Operator;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface OperatorRepository extends MongoRepository<Operator,String> {
    Optional<Operator> findByName(String name);
}

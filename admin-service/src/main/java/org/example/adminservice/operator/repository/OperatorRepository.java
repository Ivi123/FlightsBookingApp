package org.example.adminservice.operator.repository;

import org.example.adminservice.operator.model.Operator;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OperatorRepository extends MongoRepository<Operator,String> {
}

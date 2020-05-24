package com.example.app.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.app.domain.Suppression;

@Repository
public interface SuppressionRepository extends MongoRepository<Suppression, String> {

}

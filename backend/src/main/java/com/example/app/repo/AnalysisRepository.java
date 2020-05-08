package com.example.app.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.app.domain.Analysis;

@Repository
public interface AnalysisRepository extends MongoRepository<Analysis, String> {

}

package com.example.app.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.app.domain.AnalysisSAST;

@Repository
public interface AnalysisSASTRepository extends MongoRepository<AnalysisSAST, String> {

}

package edu.uoc.app.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import edu.uoc.app.domain.AnalysisSAST;

@Repository
public interface AnalysisSASTRepository extends MongoRepository<AnalysisSAST, String> {

}

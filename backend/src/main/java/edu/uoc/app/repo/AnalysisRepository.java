package edu.uoc.app.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import edu.uoc.app.domain.Analysis;

@Repository
public interface AnalysisRepository extends MongoRepository<Analysis, String> {

}

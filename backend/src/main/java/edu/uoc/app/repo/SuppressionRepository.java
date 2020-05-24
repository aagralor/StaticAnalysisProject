package edu.uoc.app.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import edu.uoc.app.domain.Suppression;

@Repository
public interface SuppressionRepository extends MongoRepository<Suppression, String> {

}

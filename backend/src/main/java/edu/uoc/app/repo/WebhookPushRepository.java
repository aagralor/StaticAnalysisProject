package edu.uoc.app.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import edu.uoc.app.domain.github.WebhookPush;

@Repository
public interface WebhookPushRepository extends MongoRepository<WebhookPush, String> {

}

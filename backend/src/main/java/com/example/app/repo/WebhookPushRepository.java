package com.example.app.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.app.domain.github.WebhookPush;

@Repository
public interface WebhookPushRepository extends MongoRepository<WebhookPush, String> {

}

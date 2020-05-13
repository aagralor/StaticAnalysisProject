package com.example.app.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.app.dto.github.WebhookInstallation;

@Repository
public interface WebhookInstallationRepository extends MongoRepository<WebhookInstallation, String> {

	WebhookInstallation findByInstallationId(Long installationId);
}

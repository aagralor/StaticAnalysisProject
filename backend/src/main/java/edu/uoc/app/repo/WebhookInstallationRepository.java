package edu.uoc.app.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import edu.uoc.app.domain.github.WebhookInstallation;

@Repository
public interface WebhookInstallationRepository extends MongoRepository<WebhookInstallation, String> {

	WebhookInstallation findByInstallationId(Long installationId);
}

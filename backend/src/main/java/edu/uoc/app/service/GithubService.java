package edu.uoc.app.service;

import java.util.List;

import edu.uoc.app.domain.Project;
import edu.uoc.app.domain.github.WebhookInstallation;
import edu.uoc.app.domain.github.WebhookPush;

public interface GithubService {

	String downloadRepository(String repoName, String branchName, String username, String accessToken);

	WebhookInstallation createWebhookInstallation(WebhookInstallation wh);

	List<Project> linkAccessToken(WebhookInstallation wh);

	WebhookInstallation getWebhookInstallationByInstId(Long instId);

	List<Project> updateAddAccessToken(WebhookInstallation wh);

	List<Project> updateRemoveAccessToken(WebhookInstallation wh);

	List<Project> removeAccessToken(WebhookInstallation wh);

	WebhookPush createWebhookPush(WebhookPush whp);

}

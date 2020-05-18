package com.example.app.service;

import java.util.List;

import com.example.app.domain.Project;
import com.example.app.domain.github.WebhookInstallation;

public interface GithubService {

	String downloadRepository(String repoName, String branchName, String username, String accessToken);

	WebhookInstallation createWebhookInstallation(WebhookInstallation wh);

	List<Project> linkAccessToken(WebhookInstallation wh);

	WebhookInstallation getWebhookInstallationByInstId(Long instId);

	List<Project> updateAccessToken(WebhookInstallation wh);

}

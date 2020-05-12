package com.example.app.service;

import com.example.app.dto.github.WebhookInstallation;

public interface GithubService {

	String downloadRepository(String repoName, String branchName, String username, String accessToken);

	WebhookInstallation createWebhookInstallation(WebhookInstallation wh);

}

package com.example.app.service;

import java.util.List;

import com.example.app.domain.Project;
import com.example.app.domain.github.WebhookInstallation;
import com.example.app.domain.github.WebhookPush;

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

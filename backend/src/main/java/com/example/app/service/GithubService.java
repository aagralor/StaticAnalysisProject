package com.example.app.service;


public interface GithubService {

	String downloadRepository(String repoName, String branchName, String username, String accessToken);

}

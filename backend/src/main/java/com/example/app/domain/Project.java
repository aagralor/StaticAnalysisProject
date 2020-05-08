package com.example.app.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "Project")
@Getter
@Setter
@NoArgsConstructor
public class Project {

	@Id
	private String id;

	@Field("Key")
	private String key;

	@Field("Name")
	private String name;

	@Field("GithubAccessToken")
	private String accessToken;

	@Field("GithubUsername")
	private String username;

	@Field("GithubEmail")
	private String email;

	@Field("GithubRepositoryName")
	private String repositoryName;

	@Field("GithubBranchName")
	private String branchName;

	@Field("GithubUrl")
	private String url;

//	@Field("AnalysisSastList")
//	private List<String> analysisSastList;

	@Field("AnalysisList")
	private List<String> analysisList;

//	public void addAnalysisSast(String anlysisSastId) {
//		if (this.analysisSastList == null) {
//			this.analysisSastList = new ArrayList<>();
//		}
//		this.analysisSastList.add(anlysisSastId);
//
//	}

	public void addAnalysis(String anlysisId) {
		if (this.analysisList == null) {
			this.analysisList = new ArrayList<>();
		}
		this.analysisList.add(anlysisId);

	}

}

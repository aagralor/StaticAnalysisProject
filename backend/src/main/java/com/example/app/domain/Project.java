package com.example.app.domain;

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

}

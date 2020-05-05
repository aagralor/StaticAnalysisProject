package com.example.app.dto.sast;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProjectDTO {

	private String id;

	private String key;

	private String name;

	private String accessToken;

	private String username;

	private String email;

	private String repositoryName;

	private String branchName;

	private String url;

}

package com.example.app.dto.github;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class WebhookInstallation {

	@Id
	private String id;

	@JsonProperty("user")
	private String username;

	private Integer installationId;

	private String repositoryId;

	private String repositoryName;

	private String bearerToken;

}

package com.example.app.domain.github;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document(collection = "WebhookPush")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class WebhookPush {

	@Id
	private String id;

	@JsonProperty("user")
	private String username;

	private String branchRef;

	private String before;

	private String after;

	private WebhookRepository repo;

}

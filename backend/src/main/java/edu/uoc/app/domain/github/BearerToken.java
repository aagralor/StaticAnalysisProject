package edu.uoc.app.domain.github;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document(collection = "BearerToken")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class BearerToken {

	@Id
	private String id;

	@JsonProperty("user")
	private String username;

	private String bearerToken;

	private List<String> repoList;

}

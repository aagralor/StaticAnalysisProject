package com.example.app.domain.sca.json;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "Reference")
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Reference {

	@Field("Source")
	private String source;

	@Field("URL")
	private String url;

	@Field("Name")
	private String name;

}

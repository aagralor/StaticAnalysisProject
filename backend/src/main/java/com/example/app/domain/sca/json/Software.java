package com.example.app.domain.sca.json;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "Software")
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Software {

	@Field("Id")
	private String id;

	@Field("VulnerabilityIdMatched")
	private String vulnerabilityIdMatched;

	@Field("VersionEndExcluding")
	private String versionEndExcluding;

	@Field("VersionEndIncluding")
	private String versionEndIncluding;

}

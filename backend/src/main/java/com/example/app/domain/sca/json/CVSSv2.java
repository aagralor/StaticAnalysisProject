package com.example.app.domain.sca.json;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document(collection = "CVSSv2")
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CVSSv2 {

	@Field("Score")
	private Double score;

	@Field("AccessVector")
	private String accessVector;

	@Field("AccessComplexity")
	private String accessComplexity;

	@Field("Authenticationr")
	private String authenticationr;

	@Field("ConfidentialImpact")
	private String confidentialImpact;

	@Field("IntegrityImpact")
	private String integrityImpact;

	@Field("AvailabilityImpact")
	private String availabilityImpact;

	@Field("Severity")
	private String severity;

}

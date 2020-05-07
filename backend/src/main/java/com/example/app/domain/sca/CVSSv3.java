package com.example.app.domain.sca;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CVSSv3 {

	private Double baseScore;

	private String attackVector;

	private String attackComplexity;

	private String privilegesRequired;

	private String userInteraction;

	private String scope;

	private String confidentialityImpact;

	private String integrityImpact;

	private String availabilityImpact;

	private String baseSeverity;

}

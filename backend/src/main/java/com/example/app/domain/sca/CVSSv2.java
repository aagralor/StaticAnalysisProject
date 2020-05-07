package com.example.app.domain.sca;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CVSSv2 {

	private Double score;

	private String accessVector;

	private String accessComplexity;

	private String authenticationr;

	private String confidentialImpact;

	private String integrityImpact;

	private String availabilityImpact;

	private String severity;

}

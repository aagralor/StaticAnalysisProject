package edu.uoc.app.domain.sca.json;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document(collection = "CVSSv3")
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CVSSv3 {

	@Field("BaseScore")
	private Double baseScore;

	@Field("AttackVector")
	private String attackVector;

	@Field("AttackComplexity")
	private String attackComplexity;

	@Field("PrivilegesRequired")
	private String privilegesRequired;

	@Field("UserInteraction")
	private String userInteraction;

	@Field("Scope")
	private String scope;

	@Field("ConfidentialityImpact")
	private String confidentialityImpact;

	@Field("IntegrityImpact")
	private String integrityImpact;

	@Field("AvailabilityImpact")
	private String availabilityImpact;

	@Field("BaseSeverity")
	private String baseSeverity;

}

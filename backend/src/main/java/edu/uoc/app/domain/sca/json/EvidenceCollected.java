package edu.uoc.app.domain.sca.json;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EvidenceCollected {

	@JsonProperty("vendorEvidence")
	private List<Evidence> vendorEvidenceList;

	@JsonProperty("productEvidence")
	private List<Evidence> productEvidenceList;

	@JsonProperty("versionEvidence")
	private List<Evidence> versionEvidenceList;

}

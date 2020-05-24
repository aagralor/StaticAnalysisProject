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
public class Dependency {

	private Boolean isVirtual;

	private String fileName;

	private String filePath;

	private String md5;

	private String sha1;

	private String sha256;

	private String description;

	private String license;

	private EvidenceCollected evidenceCollected;

	@JsonProperty("packages")
	private List<DCPackage> packageList;

	@JsonProperty("vulnerabilityIds")
	private List<VulnerabilityId> vulnerabilityIdList;

	@JsonProperty("vulnerabilities")
	private List<Vulnerability> vulnerabilityList;

}

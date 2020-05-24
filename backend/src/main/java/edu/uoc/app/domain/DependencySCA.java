package edu.uoc.app.domain;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import edu.uoc.app.domain.sca.json.DCPackage;
import edu.uoc.app.domain.sca.json.VulnerabilityId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Document(collection = "DependencySCA")
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DependencySCA {

	@Field("FileName")
	private String fileName;

	@Field("FilePath")
	private String filePath;

	@Field("SHA256")
	private String sha256;

	@Field("SHA1")
	private String sha1;

	@Field("Description")
	private String description;

	@Field("PackageList")
	private List<DCPackage> packageList;

	@Field("VulnerabilityIdList")
	private List<VulnerabilityId> vulnerabilityIdList;

	@Field("VulnerabilityList")
	private List<VulnerabilitySCA> vulnerabilityList;

}

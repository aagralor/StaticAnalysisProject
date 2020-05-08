package com.example.app.domain;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.example.app.domain.sca.json.DCPackage;
import com.example.app.domain.sca.json.VulnerabilityId;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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

	@Field("Description")
	private String description;

	@Field("PackageList")
	private List<DCPackage> packageList;

	@Field("VulnerabilityIdList")
	private List<VulnerabilityId> vulnerabilityIdList;

	@Field("VulnerabilityList")
	private List<VulnerabilitySCA> vulnerabilityList;

}

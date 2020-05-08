package com.example.app.domain.sca.json;

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
public class ScanInfo {

	private String engineVersion;

	@JsonProperty("dataSource")
	private List<DataSource> dataSourceList;

}

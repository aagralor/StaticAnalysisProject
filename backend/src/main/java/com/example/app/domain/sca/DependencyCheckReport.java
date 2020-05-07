package com.example.app.domain.sca;

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
public class DependencyCheckReport {

    private String reportSchema;

    private ScanInfo scanInfo;

    private ProjectInfo projectInfo;

	@JsonProperty("dependencies")
    private List<Dependency> dependencyList;

}

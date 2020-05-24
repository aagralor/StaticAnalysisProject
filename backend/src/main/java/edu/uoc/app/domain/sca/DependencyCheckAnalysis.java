package edu.uoc.app.domain.sca;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import edu.uoc.app.domain.sca.json.Dependency;
import edu.uoc.app.domain.sca.json.ProjectInfo;
import edu.uoc.app.domain.sca.json.ScanInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DependencyCheckAnalysis {

    private String reportSchema;

    private ScanInfo scanInfo;

    private ProjectInfo projectInfo;

	@JsonProperty("dependencies")
    private List<Dependency> dependencyList;

}
